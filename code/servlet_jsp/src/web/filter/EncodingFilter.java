package web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/*
 * 15.Filter(过滤器)
	作用:在一个请求去访问某个资源的时候,filter可以在这个请求访问到这个资源之前,把请求拦下,
	然后做出一系列的处理或者判断(比如编码的转换,信息的过滤、权限的判断、是否已经登录的验证等等),
	最后filter再决定是否要让这个请求去访问那个资源.
	如何写一个filter:
	写一个java类,然后实现javax.Servlet.Filter接口
	这个接口中有三个方法:
	init  destroy  doFilter
	init:这个过滤器类被服务器创建对象的时候会调用到这个方法。
	destroy:过滤器对象被销毁的时候会调用这个方法。
	doFilter:当过滤器拦截到请求的时候,会调用这个doFilter.
 * */
public class EncodingFilter implements Filter {

	public EncodingFilter() {
		System.out.println("filter创建");
	}
	@Override
	public void destroy() {
		System.out.println("filter销毁");
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		System.out.println("filter放行前");
		//将请求放行
		chain.doFilter(req, resp);
		System.out.println("filter放行后");
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		System.out.println("filter初始化");
	}

}
