/**
 * 
 */
package com.epweike.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.RangeFacet;
import org.apache.solr.client.solrj.response.FacetField.Count;

/**
 * @Description:图表统计工具类
 * 
 * @author 吴小平
 * @version 创建时间：2015年4月30日 上午10:00:29
 */
public class StatUtils {

	/**
	 * @Description:饼状图json数据转换
	 * 
	 * @author 吴小平
	 * @version 创建时间：2015年4月30日 上午10:00:29
	 */
	public static String pieJson(List<FacetField> facetFields) {
		// 地区分布统计
		List<Map<String, Object>> countList = getFacetList(facetFields, "pie");
		// List to json
		return JSONArray.fromObject(countList).toString();
	}

	/**
	 * @Description:柱状图json数据转换
	 * 
	 * @author 吴小平
	 * @version 创建时间：2015年4月30日 上午10:00:29
	 */
	public static List<Map<String, Object>> barJson(List<FacetField> facetFields) {
		return getFacetList(facetFields, "bar");
	}

	/**
	 * @Description:获取分组统计结果
	 * 
	 * @author 吴小平
	 * @version 创建时间：2015年6月9日 下午3:32:48
	 */
	public static List<Map<String, Object>> getFacetList(
			List<FacetField> facetFields, String chartType) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		if (chartType.equals("pie")) {// 饼图
			for (FacetField ff : facetFields) {
				System.out.println("--------------------");
				System.out.println("name=" + ff.getName() + "\tcount="
						+ ff.getValueCount());
				System.out.println("--------------------");
				switch (ff.getName()) {
				case "province":// 按省份分布统计
					for (Count count : ff.getValues()) {
						System.out.println("name=" + count.getName()
								+ "\tcount=" + count.getCount());
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("label", count.getName());
						map.put("data", count.getCount());
						list.add(map);
					}
					break;
				case "login_type":// 按登陆类型统计
					for (Count count : ff.getValues()) {
						System.out.println("name=" + count.getName()
								+ "\tcount=" + count.getCount());
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("label", count.getName());
						map.put("data", count.getCount());
						list.add(map);
					}
					break;
				case "source":// 按来源类型统计（任务、稿件）
					for (Count count : ff.getValues()) {
						System.out.println("name=" + count.getName()
								+ "\tcount=" + count.getCount());
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("label", count.getName());
						map.put("data", count.getCount());
						list.add(map);
					}
					break;
				}
			}
		} else {
			for (FacetField ff : facetFields) {
				System.out.println("--------------------");
				System.out.println("name=" + ff.getName() + "\tcount="
						+ ff.getValueCount());
				System.out.println("--------------------");
				switch (ff.getName()) {
				case "province":// 按省份分布统计
					for (Count count : ff.getValues()) {
						System.out.println("name=" + count.getName()
								+ "\tcount=" + count.getCount());
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("name", count.getName());
						map.put("count", count.getCount());
						list.add(map);
					}
					break;
				case "login_type":// 按登陆类型统计
					for (Count count : ff.getValues()) {
						System.out.println("name=" + count.getName()
								+ "\tcount=" + count.getCount());
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("name", count.getName());
						map.put("count", count.getCount());
						list.add(map);
					}
					break;
				case "source":// 按来源类型统计（任务、稿件）
					for (Count count : ff.getValues()) {
						System.out.println("name=" + count.getName()
								+ "\tcount=" + count.getCount());
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("name", count.getName());
						map.put("count", count.getCount());
						list.add(map);
					}
					break;
				case "indus_name":// 按分类统计（任务、稿件）
					for (Count count : ff.getValues()) {
						System.out.println("name=" + count.getName()
								+ "\tcount=" + count.getCount());
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("name", count.getName());
						map.put("count", count.getCount());
						list.add(map);
					}
					break;
				}
			}
		}

		return list;
	}

	/**
	 * @Description:获取区间统计列表
	 * 
	 * @author 吴小平
	 * @version 创建时间：2015年6月15日 上午11:10:17
	 */
	public static List<Map<String, Object>> getFacetRangeList(
			@SuppressWarnings("rawtypes") List<RangeFacet> listFacet,
			int endIndex) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (RangeFacet<?, ?> rf : listFacet) {
			List<RangeFacet.Count> listCounts = rf.getCounts();
			for (RangeFacet.Count count : listCounts) {
				System.out.println("RangeFacet:" + count.getValue() + ":"
						+ count.getCount());
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("date", count.getValue().substring(0, endIndex));// 日期截取特定形式
				map.put("count", count.getCount());
				list.add(map);
			}
		}
		return list;
	}

}