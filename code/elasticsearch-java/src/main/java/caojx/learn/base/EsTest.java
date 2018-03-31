package caojx.learn.base;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * ElasticSearch客户端连接服务器测试
 * @author caojx
 * Created on 2018/3/29 下午下午1:08
 */
public class EsTest {

    private static String host="192.168.46.136"; // 服务器地址

    private static int port=9300; // 9300是内部传输端口，9200是http端口，这两个不同的

    private static final String ANALYZER="smartcn";

    private TransportClient client=null;

    /**
     * 获取连接
     * @throws Exception
     */
    @Before
    public void getClient() throws Exception{
       client = new PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(EsTest.host),EsTest.port));
    }

    /**
     * 关闭连接
     */
    @After
    public void close(){
        if(client!=null){
            client.close();
        }
    }

    /**
     * 添加索引
     * @throws Exception
     */
    @Test
    public void testIndex() throws Exception{
        //三个参数 索引名称，类型，索引id
      IndexResponse response =  client.prepareIndex("twitter","tweet","1")
                .setSource(XContentFactory.jsonBuilder().startObject()
                .field("user","kimchy")
                .field("postData",new Date())
                .field("message","trying out Elasticsearch")
                .endObject()).get();
        //文档id使用自定义的id
        System.out.println("索引名称："+response.getIndex());
        System.out.println("类型："+response.getType());
        System.out.println("文档Id："+response.getId());
        System.out.println("当前实例状态："+response.status());
    }

    /**
     * 添加索引
     * @throws Exception
     */
    @Test
    public void testIndex2() throws Exception{
        String json = "{" +
                "\"user\":\"kimchy\"," +
                "\"postDate\":\"2013-01-30\"," +
                "\"message\":\"trying out Elasticsearch\"" +
                "}";
        //文档id让elastic自动生成
        IndexResponse response = client.prepareIndex("weibo","tweet")
                .setSource(json, XContentType.JSON)
                .get();
        System.out.println("索引名称："+response.getIndex());
        System.out.println("类型："+response.getType());
        System.out.println("文档Id："+response.getId());
        System.out.println("当前实例状态："+response.status());
    }


    /**
     * 添加索引
     * @throws Exception
     */
    @Test
    public void testIndex3() throws Exception{
        Map<String,Object> json = new HashMap<String,Object>();
        json.put("user","kimchy");
        json.put("postDate",new Date());
        json.put("message","trying out Elasticsearch");

        IndexResponse response = client.prepareIndex("qq","tweet")
                .setSource(json)
                .get();

        System.out.println("索引名称："+response.getIndex());
        System.out.println("类型："+response.getType());
        System.out.println("文档Id："+response.getId());
        System.out.println("当前实例状态："+response.status());
    }

    /**
     * 添加索引
     * @throws Exception
     */
    @Test
    public void testIndex4() throws Exception{
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("user","kimchy");
        jsonObject.put("postDate",new Date());
        jsonObject.put("message","trying out Elasticsearch");

        IndexResponse response = client.prepareIndex("qq","tweet")
                .setSource(jsonObject.toString(), XContentType.JSON)
                .get();

        System.out.println("索引名称："+response.getIndex());
        System.out.println("类型："+response.getType());
        System.out.println("文档Id："+response.getId());
        System.out.println("当前实例状态："+response.status());
    }

    /**
     * 获取数据
     * ElasticSearch提供了根据索引名称，类别，文档ID来获取数据
     */
    @Test
    public void testGet(){
        GetResponse getResponse=client.prepareGet("twitter", "tweet", "1").get();
        System.out.println(getResponse.getSourceAsString());
    }


    /**
     * 更新数据
     * ElasticSearch提供了根据索引名称，类别，文档ID来修改数据，修改的设置数据可以是Map，Json串，自带工具。我们实际开发一般用Json。
     */
    @Test
    public void testUpdate(){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("user", "锋哥");
        jsonObject.put("postDate", "1989-11-11");
        jsonObject.put("message", "学习Elasticsearch");

        UpdateResponse response = client.prepareUpdate("twitter", "tweet", "1").setDoc(jsonObject.toString(),XContentType.JSON).get();
        System.out.println("索引名称："+response.getIndex());
        System.out.println("类型："+response.getType());
        System.out.println("文档ID："+response.getId());
        System.out.println("当前实例状态："+response.status());
    }

    /**
     * 删除数据
     */
    @Test
    public void testDelete(){
        DeleteResponse response=client.prepareDelete("twitter", "tweet", "1").get();
        System.out.println("索引名称："+response.getIndex());
        System.out.println("类型："+response.getType());
        System.out.println("文档ID："+response.getId()); // 第一次使用是1
        System.out.println("当前实例状态："+response.status());
    }




    /**
     * 在elasticsearch-head创建了film索引并添加mapping映射，这里插入文档数据
     * @throws Exception
     */
    @Test
    public void testIndex5() throws Exception{
        JSONArray jsonArray=new JSONArray();

        JSONObject jsonObject=new JSONObject();
        jsonObject.put("title", "前任3：再见前任");
        jsonObject.put("publishDate", "2017-12-29");
        jsonObject.put("content", "一对好基友孟云（韩庚 饰）和余飞（郑恺 饰）跟女友都因为一点小事宣告分手，并且“拒绝挽回，死不认错”。两人在夜店、派对与交友软件上放飞人生第二春，大肆庆祝“黄金单身期”，从而引发了一系列好笑的故事。孟云与女友同甘共苦却难逃“五年之痒”，余飞与女友则棋逢敌手相爱相杀无绝期。然而现实的“打脸”却来得猝不及防：一对推拉纠结零往来，一对纠缠互怼全交代。两对恋人都将面对最终的选择：是再次相见？还是再也不见？");
        jsonObject.put("director", "田羽生");
        jsonObject.put("price", "35");
        jsonArray.add(jsonObject);


        JSONObject jsonObject2=new JSONObject();
        jsonObject2.put("title", "机器之血");
        jsonObject2.put("publishDate", "2017-12-29");
        jsonObject2.put("content", "2007年，Dr.James在半岛军火商的支持下研究生化人。研究过程中，生化人安德烈发生基因突变大开杀戒，将半岛军火商杀害，并控制其组织，接管生化人的研究。Dr.James侥幸逃生，只好寻求警方的保护。特工林东（成龙 饰）不得以离开生命垂危的小女儿西西，接受证人保护任务...十三年后，一本科幻小说《机器之血》的出版引出了黑衣生化人组织，神秘骇客李森（罗志祥 饰）（被杀害的半岛军火商的儿子），以及隐姓埋名的林东，三股力量都开始接近一个“普通”女孩Nancy（欧阳娜娜 饰）的生活，想要得到她身上的秘密。而黑衣人幕后受伤隐藏多年的安德烈也再次出手，在多次缠斗之后终于抓走Nancy。林东和李森，不得不以身犯险一同前去解救，关键时刻却发现李森竟然是被杀害的半岛军火商的儿子，生化人的实验记录也落入了李森之手......");
        jsonObject2.put("director", "张立嘉");
        jsonObject2.put("price", "45");
        jsonArray.add(jsonObject2);

        JSONObject jsonObject3=new JSONObject();
        jsonObject3.put("title", "星球大战8：最后的绝地武士");
        jsonObject3.put("publishDate", "2018-01-05");
        jsonObject3.put("content", "《星球大战：最后的绝地武士》承接前作《星球大战：原力觉醒》的剧情，讲述第一军团全面侵袭之下，蕾伊（黛西·雷德利 Daisy Ridley 饰）、芬恩（约翰·博耶加 John Boyega 饰）、波·达默龙（奥斯卡·伊萨克 Oscar Isaac 饰）三位年轻主角各自的抉 择和冒险故事。前作中觉醒强大原力的蕾伊独自寻访隐居的绝地大师卢克·天行者（马克·哈米尔 Mark Hamill 饰），在后者的指导下接受原力训练。芬恩接受了一项几乎不可能完成的任务，为此他不得不勇闯敌营，面对自己的过去。波·达默龙则要适应从战士向领袖的角色转换，这一过程中他也将接受一些血的教训。");
        jsonObject3.put("director", "莱恩·约翰逊");
        jsonObject3.put("price", "55");
        jsonArray.add(jsonObject3);

        JSONObject jsonObject4=new JSONObject();
        jsonObject4.put("title", "羞羞的铁拳");
        jsonObject4.put("publishDate", "2017-12-29");
        jsonObject4.put("content", "靠打假拳混日子的艾迪生（艾伦 饰），本来和正义感十足的体育记者马小（马丽 饰）是一对冤家，没想到因为一场意外的电击，男女身体互换。性别错乱后，两人互坑互害，引发了拳坛的大地震，也揭开了假拳界的秘密，惹来一堆麻烦，最终两人在“卷莲门”副掌门张茱萸（沈腾 饰）的指点下，向恶势力挥起了羞羞的铁拳。");
        jsonObject4.put("director", "宋阳 / 张吃鱼");
        jsonObject4.put("price", "35");
        jsonArray.add(jsonObject4);

        JSONObject jsonObject5=new JSONObject();
        jsonObject5.put("title", "战狼2");
        jsonObject5.put("publishDate", "2017-07-27");
        jsonObject5.put("content", "故事发生在非洲附近的大海上，主人公冷锋（吴京 饰）遭遇人生滑铁卢，被“开除军籍”，本想漂泊一生的他，正当他打算这么做的时候，一场突如其来的意外打破了他的计划，突然被卷入了一场非洲国家叛乱，本可以安全撤离，却因无法忘记曾经为军人的使命，孤身犯险冲回沦陷区，带领身陷屠杀中的同胞和难民，展开生死逃亡。随着斗争的持续，体内的狼性逐渐复苏，最终孤身闯入战乱区域，为同胞而战斗。");
        jsonObject5.put("director", "吴京");
        jsonObject5.put("price", "38");
        jsonArray.add(jsonObject5);

        for(int i=0;i<jsonArray.size();i++){
            JSONObject jo= (JSONObject) jsonArray.get(i);
            IndexResponse response=client.prepareIndex("film", "dongzuo")
                    .setSource(jo.toString(), XContentType.JSON).get();
            System.out.println("索引名称："+response.getIndex());
            System.out.println("类型："+response.getType());
            System.out.println("文档ID："+response.getId());
            System.out.println("当前实例状态："+response.status());
        }
    }

    /**
     * 查询所有所用film 数据
     * @throws Exception
     */
    @Test
    public void searchAll()throws Exception{
        SearchRequestBuilder srb=client.prepareSearch("film").setTypes("dongzuo");
        SearchResponse sr=srb.setQuery(QueryBuilders.matchAllQuery()).execute().actionGet(); // 查询所有
        SearchHits hits=sr.getHits();
        for(SearchHit hit:hits){
            System.out.println(hit.getSourceAsString());
        }
    }

    //-----------------------------elasticsearch分页查询数据restful api以及java代码实现------------

    /**
     * 分页查询
     * @throws Exception
     */
    @Test
    public void searchPaging()throws Exception{
        SearchRequestBuilder srb=client.prepareSearch("film").setTypes("dongzuo");
        SearchResponse sr=srb.setQuery(QueryBuilders.matchAllQuery()).setFrom(1).setSize(2).execute().actionGet(); // 查询所有
        SearchHits hits=sr.getHits();
        for(SearchHit hit:hits){
            System.out.println(hit.getSourceAsString());
        }
    }

    //-----------------------------elasticsearch排序查询数据restful api以及java代码实现------------
    /**
     * 排序查询
     * @throws Exception
     */
    @Test
    public void searchOrderBy()throws Exception{
        SearchRequestBuilder srb=client.prepareSearch("film").setTypes("dongzuo");
        SearchResponse sr=srb.setQuery(QueryBuilders.matchAllQuery())
                .addSort("publishDate", SortOrder.DESC)
                .execute()
                .actionGet(); // 分页排序所有
        SearchHits hits=sr.getHits();
        for(SearchHit hit:hits){
            System.out.println(hit.getSourceAsString());
        }
    }

    //----------------------------elasticsearch数据列过滤restful api以及java代码实现-------------

    /**
     * 数据列过滤，相当于只需要返回对应的结果列
     * @throws Exception
     */
    @Test
    public void searchInclude()throws Exception{
        SearchRequestBuilder srb=client.prepareSearch("film").setTypes("dongzuo");
        SearchResponse sr=srb.setQuery(QueryBuilders.matchAllQuery())
                .setFetchSource(new String[]{"title","price"}, null)
                .execute()
                .actionGet(); // 分页排序所有
        SearchHits hits=sr.getHits();
        for(SearchHit hit:hits){
            System.out.println(hit.getSourceAsString());
        }
    }


    //----------------------------elasticsearch简单条件查询restful api以及java代码实现-----------
    /**
     * 简单条件查询
     * @throws Exception
     */
    @Test
    public void searchByCondition()throws Exception{
        SearchRequestBuilder srb=client.prepareSearch("film").setTypes("dongzuo");
        SearchResponse sr=srb.setQuery(QueryBuilders.matchQuery("title", "战"))
                .setFetchSource(new String[]{"title","price"}, null)
                .execute()
                .actionGet(); // 分页排序所有
        SearchHits hits=sr.getHits();
        for(SearchHit hit:hits){
            System.out.println(hit.getSourceAsString());
        }
    }

    //-------------------------elasticsearch条件查询高亮实现restful api以及java代码实现-----------

    /**
     * 条件查询高亮实现,使用指定的标识符号对返回结果进行高亮
     * @throws Exception
     */
    @Test
    public void searchHighlight()throws Exception{
        SearchRequestBuilder srb=client.prepareSearch("film").setTypes("dongzuo");
        HighlightBuilder highlightBuilder=new HighlightBuilder();
        highlightBuilder.preTags("<h2>");
        highlightBuilder.postTags("</h2>");
        highlightBuilder.field("title");
        SearchResponse sr=srb.setQuery(QueryBuilders.matchQuery("title", "战"))
                .highlighter(highlightBuilder)
                .setFetchSource(new String[]{"title","price"}, null)
                .execute()
                .actionGet(); // 分页排序所有
        SearchHits hits=sr.getHits();
        for(SearchHit hit:hits){
            System.out.println(hit.getSourceAsString());
            System.out.println(hit.getHighlightFields());
        }
    }


    //--------------------------elasticsearch组合多条件查询实现restful api以及java代码实现---------------

    /**
     * 多条件查询 must
     * @throws Exception
     */
    @Test
    public void searchMutil()throws Exception{
        SearchRequestBuilder srb=client.prepareSearch("film").setTypes("dongzuo");
        QueryBuilder queryBuilder=QueryBuilders.matchPhraseQuery("title", "战");
        QueryBuilder queryBuilder2=QueryBuilders.matchPhraseQuery("content", "星球");
        SearchResponse sr=srb.setQuery(QueryBuilders.boolQuery()
                .must(queryBuilder)
                .must(queryBuilder2))
                .execute()
                .actionGet();
        SearchHits hits=sr.getHits();
        for(SearchHit hit:hits){
            System.out.println(hit.getSourceAsString());
        }
    }

    /**
     * 多条件查询 most not
     * @throws Exception
     */
    @Test
    public void searchMutil2()throws Exception{
        SearchRequestBuilder srb=client.prepareSearch("film").setTypes("dongzuo");
        QueryBuilder queryBuilder=QueryBuilders.matchPhraseQuery("title", "战");
        QueryBuilder queryBuilder2=QueryBuilders.matchPhraseQuery("content", "武士");
        SearchResponse sr=srb.setQuery(QueryBuilders.boolQuery()
                .must(queryBuilder)
                .mustNot(queryBuilder2))
                .execute()
                .actionGet();
        SearchHits hits=sr.getHits();
        for(SearchHit hit:hits){
            System.out.println(hit.getSourceAsString());
        }
    }

    /**
     * 多条件查询 should
     * @throws Exception
     */
    @Test
    public void searchMutil3()throws Exception{
        SearchRequestBuilder srb=client.prepareSearch("film").setTypes("dongzuo");
        QueryBuilder queryBuilder=QueryBuilders.matchPhraseQuery("title", "战");
        QueryBuilder queryBuilder2=QueryBuilders.matchPhraseQuery("content", "星球");
        QueryBuilder queryBuilder3=QueryBuilders.rangeQuery("publishDate").gt("2018-01-01");
        SearchResponse sr=srb.setQuery(QueryBuilders.boolQuery()
                .must(queryBuilder)
                .should(queryBuilder2)
                .should(queryBuilder3))
                .execute()
                .actionGet();
        SearchHits hits=sr.getHits();
        for(SearchHit hit:hits){
            System.out.println(hit.getScore()+":"+hit.getSourceAsString());
        }
    }

    /**
     * 多条件查询 filter
     * @throws Exception
     */
    @Test
    public void searchMutil4()throws Exception{
        SearchRequestBuilder srb=client.prepareSearch("film").setTypes("dongzuo");
        QueryBuilder queryBuilder=QueryBuilders.matchPhraseQuery("title", "战");
        QueryBuilder queryBuilder2=QueryBuilders.rangeQuery("price").lte(40);
        SearchResponse sr=srb.setQuery(QueryBuilders.boolQuery()
                .must(queryBuilder)
                .filter(queryBuilder2))
                .execute()
                .actionGet();
        SearchHits hits=sr.getHits();
        for(SearchHit hit:hits){
            System.out.println(hit.getSourceAsString());
        }
    }


    //---------------------------中文分词查询---------------------
    /**
     * 条件分词查询
     * @throws Exception
     */
    @Test
    public void search()throws Exception{
        SearchRequestBuilder srb=client.prepareSearch("film2").setTypes("dongzuo");
        SearchResponse sr=srb.setQuery(QueryBuilders.matchQuery("title", "星球狼").analyzer(ANALYZER))
                .setFetchSource(new String[]{"title","price"}, null)
                .execute()
                .actionGet();
        SearchHits hits=sr.getHits();
        for(SearchHit hit:hits){
            System.out.println(hit.getSourceAsString());
        }
    }

    /**
     * 多字段条件分词查询
     * @throws Exception
     */
    @Test
    public void search2()throws Exception{
        SearchRequestBuilder srb=client.prepareSearch("film2").setTypes("dongzuo");
        SearchResponse sr=srb.setQuery(QueryBuilders.multiMatchQuery("非洲星球", "title","content").analyzer(ANALYZER))
                .setFetchSource(new String[]{"title","price","content"}, null)
                .execute()
                .actionGet();
        SearchHits hits=sr.getHits();
        for(SearchHit hit:hits){
            System.out.println(hit.getSourceAsString());
        }
    }
}
