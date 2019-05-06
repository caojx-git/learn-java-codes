

# Spring getBean和createBean源码分析

org.springframework.beans.factory.support.AbstractBeanFactory



## 1. getBean

org.springframework.beans.factory.support.AbstractBeanFactory#doGetBean

```java
protected <T> T doGetBean(
			final String name, final Class<T> requiredType, final Object[] args, boolean typeCheckOnly)
			throws BeansException {
        //转化beanName，如果包含&，删除之 
		//通过name（name可能是别名，所以要处理下）获取beanName（对于一个类而言，是唯一的）
		final String beanName = transformedBeanName(name);
		Object bean;

    	// 先从缓存中去取,处理已经被创建过的单利模式的bean，对这种bean的请求不需要重复地创建。  
		// Eagerly check singleton cache for manually registered singletons.
       //bean！=null，也就是取到了，那么判断是不是工厂类，如果是，则替换成getObject()方法的返回值
		Object sharedInstance = getSingleton(beanName);
		if (sharedInstance != null && args == null) {
			if (logger.isDebugEnabled()) {
				if (isSingletonCurrentlyInCreation(beanName)) {
					logger.debug("Returning eagerly cached instance of singleton bean '" + beanName +
							"' that is not fully initialized yet - a consequence of a circular reference");
				}
				else {
					logger.debug("Returning cached instance of singleton bean '" + beanName + "'");
				}
			}
            //真正的bean 返回处理
			bean = getObjectForBeanInstance(sharedInstance, name, beanName, null);
		}

		else {
			// Fail if we're already creating this bean instance:
			// We're assumably within a circular reference.
            // 假设循环依赖的时候，如果我们已经开始在当前线程中创建此bean实例，但是还没有创建完成，则失败；例如此时是A依赖于B，但是B不依赖于A，B也没有任何属性依赖于A，则不存在循环依赖，那么无论B初始化，未初始化都不会有以下情况。但是如果B依赖于A，A在获取依赖的Bean是激活创建B的方法，那么B创建过程中就会出现以下情况。就会出现循环依赖错误。如果A，B 是单例的并且A的构造函数不包含B，B的构造函数不包含A，spring还是可以通过提前暴露实例地址处理这种依赖，但是其它情况spring也无能为力了。循环依赖也是一个大问题。。。
			if (isPrototypeCurrentlyInCreation(beanName)) {
				throw new BeanCurrentlyInCreationException(beanName);
			}

			// Check if bean definition exists in this factory.
             // 判断工厂中是否含有此Bean的定义
			BeanFactory parentBeanFactory = getParentBeanFactory();
			if (parentBeanFactory != null && !containsBeanDefinition(beanName)) {
				// Not found -> check parent.
                // 如果没有，查询父工厂
				String nameToLookup = originalBeanName(name);
				if (args != null) {
					// Delegation to parent with explicit args.
                    // 执行带有args参数的getbean方法
					return (T) parentBeanFactory.getBean(nameToLookup, args);
				}
				else {
					// No args -> delegate to standard getBean method.
                    //如果没有参数，执行标准的getbean方法 
					return parentBeanFactory.getBean(nameToLookup, requiredType);
				}
			}

            //如果不仅仅是做类型检查，那么需要标记此Bean正在创建之中
			if (!typeCheckOnly) {
				markBeanAsCreated(beanName);
			}

			try {
				final RootBeanDefinition mbd = getMergedLocalBeanDefinition(beanName);
				checkMergedBeanDefinition(mbd, beanName, args);

				// Guarantee initialization of beans that the current bean depends on.
                // 获取依赖的Bean
				String[] dependsOn = mbd.getDependsOn();
				if (dependsOn != null) {
					for (String dep : dependsOn) {
						if (isDependent(beanName, dep)) {
							throw new BeanCreationException(mbd.getResourceDescription(), beanName,
									"Circular depends-on relationship between '" + beanName + "' and '" + dep + "'");
						}
						registerDependentBean(dep, beanName);
						getBean(dep);
					}
				}

				// Create bean instance.
                // 这里终于开始创建Bean实例了，如果是单例的，那么会创建一个单例的匿名工厂，如果是原型模式的，则不需要创建单例的工厂的，其他的如request、session作用域的，则根据自身的需要
				if (mbd.isSingleton()) {
					sharedInstance = getSingleton(beanName, new ObjectFactory<Object>() {
						@Override
						public Object getObject() throws BeansException {
							try {
								return createBean(beanName, mbd, args);
							}
							catch (BeansException ex) {
								// Explicitly remove instance from singleton cache: It might have been put there
								// eagerly by the creation process, to allow for circular reference resolution.
								// Also remove any beans that received a temporary reference to the bean.
								destroySingleton(beanName);
								throw ex;
							}
						}
					});
					bean = getObjectForBeanInstance(sharedInstance, name, beanName, mbd);
				}

				else if (mbd.isPrototype()) {
					// It's a prototype -> create a new instance.
					Object prototypeInstance = null;
					try {
                        //把当前的beanName加入正在创建beanName的集合中，会在第一步判断
						beforePrototypeCreation(beanName);
						prototypeInstance = createBean(beanName, mbd, args);
					}
					finally {
                        //把当前的beanName从正在创建beanName的集合中移除
						afterPrototypeCreation(beanName);
					}
					bean = getObjectForBeanInstance(prototypeInstance, name, beanName, mbd);
				}

				else {
					String scopeName = mbd.getScope();
					final Scope scope = this.scopes.get(scopeName);
					if (scope == null) {
						throw new IllegalStateException("No Scope registered for scope name '" + scopeName + "'");
					}
					try {
						Object scopedInstance = scope.get(beanName, new ObjectFactory<Object>() {
							@Override
							public Object getObject() throws BeansException {
								beforePrototypeCreation(beanName);
								try {
									return createBean(beanName, mbd, args);
								}
								finally {
									afterPrototypeCreation(beanName);
								}
							}
						});
						bean = getObjectForBeanInstance(scopedInstance, name, beanName, mbd);
					}
					catch (IllegalStateException ex) {
						throw new BeanCreationException(beanName,
								"Scope '" + scopeName + "' is not active for the current thread; consider " +
								"defining a scoped proxy for this bean if you intend to refer to it from a singleton",
								ex);
					}
				}
			}
			catch (BeansException ex) {
				cleanupAfterBeanCreationFailure(beanName);
				throw ex;
			}
		}

		// Check if required type matches the type of the actual bean instance.
        // 检查类型，如果不满足可以进行类型转换，如果转换器进行不了这两种类型的转换，则抛出异常
		if (requiredType != null && bean != null && !requiredType.isAssignableFrom(bean.getClass())) {
			try {
				return getTypeConverter().convertIfNecessary(bean, requiredType);
			}
			catch (TypeMismatchException ex) {
				if (logger.isDebugEnabled()) {
					logger.debug("Failed to convert bean '" + name + "' to required type '" +
							ClassUtils.getQualifiedName(requiredType) + "'", ex);
				}
				throw new BeanNotOfRequiredTypeException(name, requiredType, bean.getClass());
			}
		}
		return (T) bean;
	}
```





## 2. createBean

org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory#createBean(java.lang.String, org.springframework.beans.factory.support.RootBeanDefinition, java.lang.Object[])

```java
protected Object createBean(String beanName, RootBeanDefinition mbd, Object[] args) throws BeanCreationException {
		if (logger.isDebugEnabled()) {
			logger.debug("Creating instance of bean '" + beanName + "'");
		}
		RootBeanDefinition mbdToUse = mbd;

		// Make sure bean class is actually resolved at this point, and
		// clone the bean definition in case of a dynamically resolved Class
		// which cannot be stored in the shared merged bean definition.
      // 确定并加载bean的class
		Class<?> resolvedClass = resolveBeanClass(mbd, beanName);
		if (resolvedClass != null && !mbd.hasBeanClass() && mbd.getBeanClassName() != null) {
			mbdToUse = new RootBeanDefinition(mbd);
			mbdToUse.setBeanClass(resolvedClass);
		}

		// Prepare method overrides.
       // 验证以及准备需要覆盖的方法
		try {
			mbdToUse.prepareMethodOverrides();
		}
		catch (BeanDefinitionValidationException ex) {
			throw new BeanDefinitionStoreException(mbdToUse.getResourceDescription(),
					beanName, "Validation of method overrides failed", ex);
		}

		try {
			// Give BeanPostProcessors a chance to return a proxy instead of the target bean instance.
            // 给BeanPostProcessors 一个机会来返回代理对象来代替真正的实例，在这里实现创建代理对象功能
			Object bean = resolveBeforeInstantiation(beanName, mbdToUse);
			if (bean != null) {
				return bean;
			}
		}
		catch (Throwable ex) {
			throw new BeanCreationException(mbdToUse.getResourceDescription(), beanName,
					"BeanPostProcessor before instantiation of bean failed", ex);
		}

    	//创建bean
		Object beanInstance = doCreateBean(beanName, mbdToUse, args);
		if (logger.isDebugEnabled()) {
			logger.debug("Finished creating instance of bean '" + beanName + "'");
		}
		return beanInstance;
	}
```



## 3. resolveBeforeInstantiation 初始化前的后处理器：Instantiation[Aware](https://www.baidu.com/s?wd=Aware&tn=24004469_oem_dg&rsv_dl=gh_pl_sl_csd)BeanPostProcessor

createBean方法中调用了**resolveBeforeInstantiation** 方法做些前置处理，并且如果在这个方法中返回了bean的话（AOP代理对象或者自定义对象），就直接返回这个bean，不再继续后面的创建步骤，方法实现：

```java
protected Object resolveBeforeInstantiation(String beanName, RootBeanDefinition mbd) {
		Object bean = null;
    	/bean还未被解析
		if (!Boolean.FALSE.equals(mbd.beforeInstantiationResolved)) {
			// Make sure bean class is actually resolved at this point.
			if (!mbd.isSynthetic() && hasInstantiationAwareBeanPostProcessors()) {
				Class<?> targetType = determineTargetType(beanName, mbd);
				if (targetType != null) {
                    //应用实例化前的后处理器
					bean = applyBeanPostProcessorsBeforeInstantiation(targetType, beanName);
					if (bean != null) {
                        //如果bean不为空，则应用实例化后的后处理器
						bean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
					}
				}
			}
            //标记当前bean是否被解析过
			mbd.beforeInstantiationResolved = (bean != null);
		}
		return bean;
	}
```



## 3. doCreateBean

```java
protected Object doCreateBean(final String beanName, final RootBeanDefinition mbd, final Object[] args)
			throws BeanCreationException {

		// Instantiate the bean.
       //先从factoryBeanInstanceCache缓存中尝试获取
		BeanWrapper instanceWrapper = null;
		if (mbd.isSingleton()) {
			instanceWrapper = this.factoryBeanInstanceCache.remove(beanName);
		}
        //如果缓存中不存在，则根据bean对应的策略创建新的实例，如：工厂方法、构造器自动注入、简单初始化
		if (instanceWrapper == null) {
			instanceWrapper = createBeanInstance(beanName, mbd, args);
		}
		final Object bean = (instanceWrapper != null ? instanceWrapper.getWrappedInstance() : null);
		Class<?> beanType = (instanceWrapper != null ? instanceWrapper.getWrappedClass() : null);
		mbd.resolvedTargetType = beanType;

		// Allow post-processors to modify the merged bean definition.
        // 应用MergedBeanDefinitionPostProcessor 后处理器，合并bean的定义信息
		// Autowire等注解信息就是在这一步完成预解析，并且将注解需要的信息放入缓存
		synchronized (mbd.postProcessingLock) {
			if (!mbd.postProcessed) {
				try {
					applyMergedBeanDefinitionPostProcessors(mbd, beanType, beanName);
				}
				catch (Throwable ex) {
					throw new BeanCreationException(mbd.getResourceDescription(), beanName,
							"Post-processing of merged bean definition failed", ex);
				}
				mbd.postProcessed = true;
			}
		}

		// Eagerly cache singletons to be able to resolve circular references
		// even when triggered by lifecycle interfaces like BeanFactoryAware.
        // 是否需要提前曝光=单例&允许循环依赖&bean正在创建中
		boolean earlySingletonExposure = (mbd.isSingleton() && this.allowCircularReferences &&
				isSingletonCurrentlyInCreation(beanName));
		if (earlySingletonExposure) {
			if (logger.isDebugEnabled()) {
				logger.debug("Eagerly caching bean '" + beanName +
						"' to allow for resolving potential circular references");
			}
            //为了避免循环依赖，在bean初始化完成前，就将创建bean实例的ObjectFactory放入工厂缓存（singletonFactories）
			addSingletonFactory(beanName, new ObjectFactory<Object>() {
				@Override
				public Object getObject() throws BeansException {
					return getEarlyBeanReference(beanName, mbd, bean);
				}
			});
		}

		// Initialize the bean instance.
        // 对bean属性进行填充，注入bean中的属性，会递归初始化依赖的bean
		Object exposedObject = bean;
		try {
            //调用初始化方法，比如init-method、注入Aware对象、应用后处理器
			populateBean(beanName, mbd, instanceWrapper);
			if (exposedObject != null) {
				exposedObject = initializeBean(beanName, exposedObject, mbd);
			}
		}
		catch (Throwable ex) {
			if (ex instanceof BeanCreationException && beanName.equals(((BeanCreationException) ex).getBeanName())) {
				throw (BeanCreationException) ex;
			}
			else {
				throw new BeanCreationException(
						mbd.getResourceDescription(), beanName, "Initialization of bean failed", ex);
			}
		}

		if (earlySingletonExposure) {
            //从提前曝光的bean缓存中查询bean，目的是验证是否有循环依赖存在
			//如果存在循环依赖，也就是说该bean已经被其他bean递归加载过，放入了提早曝光的bean缓存中
			Object earlySingletonReference = getSingleton(beanName, false);
			if (earlySingletonReference != null) {
                //如果exposedObject没有在 initializeBean 初始化方法中改变，也就是没有被增强
				if (exposedObject == bean) {
					exposedObject = earlySingletonReference;
				}
				else if (!this.allowRawInjectionDespiteWrapping && hasDependentBean(beanName)) {
					String[] dependentBeans = getDependentBeans(beanName);
					Set<String> actualDependentBeans = new LinkedHashSet<String>(dependentBeans.length);
					for (String dependentBean : dependentBeans) {
						if (!removeSingletonIfCreatedForTypeCheckOnly(dependentBean)) {
							actualDependentBeans.add(dependentBean);
						}
					}
                    
                    //因为bean创建后，其依赖的bean一定也是已经创建的
					//如果actualDependentBeans不为空，则表示依赖的bean并没有被创建完，即存在循环依赖
					if (!actualDependentBeans.isEmpty()) {
						throw new BeanCurrentlyInCreationException(beanName,
								"Bean with name '" + beanName + "' has been injected into other beans [" +
								StringUtils.collectionToCommaDelimitedString(actualDependentBeans) +
								"] in its raw version as part of a circular reference, but has eventually been " +
								"wrapped. This means that said other beans do not use the final version of the " +
								"bean. This is often the result of over-eager type matching - consider using " +
								"'getBeanNamesOfType' with the 'allowEagerInit' flag turned off, for example.");
					}
				}
			}
		}

		// Register bean as disposable.
		try {
			registerDisposableBeanIfNecessary(beanName, bean, mbd);
		}
		catch (BeanDefinitionValidationException ex) {
			throw new BeanCreationException(
					mbd.getResourceDescription(), beanName, "Invalid destruction signature", ex);
		}

		return exposedObject;
	}
```



1. 如果是单例则首先清除缓存

2. 实例化bean，并使用BeanWarpper包装

   1. 如果存在工厂方法，则使用工厂方法实例化

   2. 如果有多个构造函数，则根据传入的参数确定构造函数进行初始化

   3. 使用默认的构造函数初始化

      

3. 应用MergedBeanDefinitionPostProcessor，Autowired注解就是在这样完成的解析工作

4. 依赖处理。如果A和B存在循环依赖，那么Spring在创建B的时候，需要自动注入A时，并不会直接创建再次创建A，而是通过放入缓存中A的ObjectFactory来创建实例，这样就解决了循环依赖的问题

5. 属性填充。所有需要的属性都在这一步注入到bean

6. 循环依赖检查

7. 注册DisposableBean。如果配置了destroy-method，这里需要注册，以便在销毁时调用

8. 完成创建并返回

---------------------

## 4.参考文章

https://blog.csdn.net/finalcola/article/details/81449140 



