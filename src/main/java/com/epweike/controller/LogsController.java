package com.epweike.controller;

import java.io.IOException;
import java.util.List;

import com.epweike.util.StatUtils;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author wuxp
 */
@Controller
public class LogsController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(LogsController.class);
	
	@RequestMapping(value = {"/logs/stat"})
    public ModelAndView stat() throws SolrServerException, IOException {
		
		SolrQuery parameters = new SolrQuery("*:*").setFacet(true).addFacetField("province");
		QueryResponse response = getSolrServer("logs").query(parameters);
		SolrDocumentList results = response.getResults();
		
		//地区分布统计
		List<FacetField> facetFields = response.getFacetFields(); 
		
		//返回视图
		ModelAndView mv = new ModelAndView("charts/users/province");
		//总数
		mv.addObject("total", results.getNumFound());
		//柱状图数据
		mv.addObject("barData", StatUtils.barJson(facetFields));
		logger.info("进入网站流量统计！！！");
        return mv;
    }
	
	@RequestMapping(value = {"/logs/userAgent"})
    public ModelAndView userAgent() throws SolrServerException, IOException {
		
		SolrQuery parameters = new SolrQuery("*:*").setFacet(true).addFacetField("province");
		QueryResponse response = getSolrServer("logs").query(parameters);
		SolrDocumentList results = response.getResults();
		
		//地区分布统计
		List<FacetField> facetFields = response.getFacetFields(); 
		
		//返回视图
		ModelAndView mv = new ModelAndView("charts/users/province");
		//总数
		mv.addObject("total", results.getNumFound());
		//柱状图数据
		mv.addObject("barData", StatUtils.barJson(facetFields));
		logger.info("进入访客来源统计！！！");
        return mv;
    }
	
}