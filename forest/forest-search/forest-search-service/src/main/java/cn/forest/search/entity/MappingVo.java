package cn.forest.search.entity;

public class MappingVo {

	 /*"type" : "text", #是数据类型一般文本使用text(可分词进行模糊查询)；keyword无法被分词(不需要执行分词器)，用于精确查找

	    "analyzer" : "ik_max_word", #指定分词器，一般使用最大分词：ik_max_word
	    
	    "normalizer" : "normalizer_name", #字段标准化规则；如把所有字符转为小写；具体如下举例

	    "boost" : 1.5, #字段权重；用于查询时评分，关键字段的权重就会高一些，默认都是1；另外查询时可临时指定权重

	    "coerce" : true, #清理脏数据：1，字符串会被强制转换为整数 2，浮点数被强制转换为整数；默认为true

	    "copy_to" : "field_name", #自定_all字段；指定某几个字段拼接成自定义；具体如下举例

	    "doc_values" : true, #加快排序、聚合操作，但需要额外存储空间；默认true，对于确定不需要排序和聚合的字段可false

	    "dynamic" : true, #新字段动态添加 true:无限制 false:数据可写入但该字段不保留 'strict':无法写入抛异常

	    "enabled" : true, #是否会被索引，但都会存储;可以针对一整个_doc

	    "fielddata" : false, #针对text字段加快排序和聚合（doc_values对text无效）；此项官网建议不开启，非常消耗内存

	    "eager_global_ordinals": true, #是否开启全局预加载,加快查询；此参数只支持text和keyword，keyword默认可用，而text需要设置fielddata属性
	    
	    "format" : "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis" ,#格式化 此参数代表可接受的时间格式 3种都接受

	    "ignore_above" : 100, #指定字段索引和存储的长度最大值，超过最大值的会被忽略

	    "ignore_malformed" : false ,#插入文档时是否忽略类型 默认是false 类型不一致无法插入

	    "index_options" : "docs" ,
	    # 4个可选参数
	    # docs（索引文档号）,
	    # freqs（文档号 + 词频），
	    # positions（文档号 + 词频 + 位置，通常用来距离查询），
	    # offsets（文档号 + 词频 + 位置 + 偏移量，通常被使用在高亮字段）
	    # 分词字段默认是position，其他的默认是docs

	    "index" : true, #该字段是否会被索引和可查询 默认true

	    "fields": {"raw": {"type": "keyword"}} ,#可以对一个字段提供多种索引模式，使用text类型做全文检索，也可使用keyword类型做聚合和排序

	    "norms" : true, #用于标准化文档，以便查询时计算文档的相关性。建议不开启

	    "null_value" : "NULL", #可以让值为null的字段显式的可索引、可搜索

	    "position_increment_gap" : 0 ,#词组查询时可以跨词查询 既可变为分词查询 默认100

	    "properties" : {}, #嵌套属性，例如该字段是音乐，音乐还有歌词，类型，歌手等属性

	    "search_analyzer" : "ik_max_word" ,#查询分词器;一般情况和analyzer对应
	    
	    "similarity" : "BM25",#用于指定文档评分模型，参数有三个：
	    # BM25 ：ES和Lucene默认的评分模型
	    # classic ：TF/IDF评分
	    # boolean：布尔模型评分

	    "store" : true, #默认情况false,其实并不是真没有存储，_source字段里会保存一份原始文档。
	    # 在某些情况下，store参数有意义，比如一个文档里面有title、date和超大的content字段，如果只想获取title和date

	    "term_vector" : "no" #默认不存储向量信息，
	    # 支持参数yes（term存储），
	    # with_positions（term + 位置）,
	    # with_offsets（term + 偏移量），
	    # with_positions_offsets(term + 位置 + 偏移量)
	    # 对快速高亮fast vector highlighter能提升性能，但开启又会加大索引体积，不适合大数据量用*/
	
	
	/**
	 * 字段名称
	 */
	private String field;
	
	/**
	 * 类型
	 */
	private String type;
	
	/**
	 * 字段分词1：ik_smart  2：ik_max_word 3 不需要分词 适用于精确查找 
	 */
	private Integer participle;
	

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getParticiple() {
		return participle;
	}

	public void setParticiple(Integer participle) {
		this.participle = participle;
	}

	
	
}
