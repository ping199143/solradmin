package com.epweike.controller;

import com.epweike.model.Lexicons;
import com.epweike.model.PageModel;
import com.epweike.model.RetModel;
import com.epweike.service.LexiconsService;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wuxp
 */
@Controller
@RequestMapping("/lexicons")
public class LexiconsController extends BaseController {

	private static final Logger logger = LoggerFactory
			.getLogger(LexiconsController.class);

	@Autowired
	private LexiconsService lexiconService;

	public List<Lexicons> lexiconList;

	@RequestMapping(value = { "list" })
	public String list(Model model) {
		return "lexicon/list";
	}

	@RequestMapping(value = "add", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody RetModel add(HttpServletRequest request)
			throws IOException {
		//返回结果对象
        RetModel retModel = new RetModel();
		// 获取请求参数
		String word = request.getParameter("word");
		String pinyin = request.getParameter("pinyin");
		String pos = request.getParameter("pos");
		String synonym = request.getParameter("synonym");
		// 数据校验
		if ("".equals(word)) {
			retModel.setFlag(false);
			retModel.setMsg("词条不能为空！");
			return retModel;
		} else {// 校验词条是否存在
			Lexicons lexicons = new Lexicons(word);
			lexicons = lexiconService.selectOne(lexicons);
			if (lexicons != null) {
				retModel.setFlag(false);
				retModel.setMsg("词条:'" + word + "'已存在！");
				return retModel;
			}
		}

		if (retModel.isFlag()) {
			try {
				Lexicons lexicons = new Lexicons();
				lexicons.setWord(word);
				lexicons.setPinyin(pinyin);
				lexicons.setPos(pos);
				lexicons.setSynonym(synonym);
				// 更新数据库
				int result = lexiconService.insert(lexicons);
				if(result > 0){
					retModel.setMsg("新增成功！");
				}else {
					retModel.setFlag(false);
					retModel.setMsg("新增失败！");
					return retModel;
				}
			} catch (Exception e) {
				e.printStackTrace();
				retModel.setFlag(false);
				retModel.setMsg("新增失败！");
				return retModel;
			}
		}
		return retModel;
	}

	@RequestMapping(value = "del", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public @ResponseBody RetModel RetModel(HttpServletRequest request)
			throws IOException {
		//返回结果对象
        RetModel retModel = new RetModel();
		// 获取主键
		int id = Integer.parseInt(request.getParameter("id"));

		try {
			this.lexiconService.delete(id);
			retModel.setMsg("删除成功！");
		} catch (Exception e) {
			retModel.setFlag(false);
			retModel.setMsg("删除失败！");
			retModel.setObj(e);
			e.printStackTrace();
		}

		return retModel;
	}

	@RequestMapping(value = "update", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody RetModel update(HttpServletRequest request)
			throws IOException {
		//返回结果对象
        RetModel retModel = new RetModel();
		// 获取请求参数
		int id = Integer.parseInt(request.getParameter("id"));
		String word = request.getParameter("word");
		String pinyin = request.getParameter("pinyin");
		String pos = request.getParameter("pos");
		String synonym = request.getParameter("synonym");
		// 数据校验
		if ("".equals(word)) {
			retModel.setFlag(false);
			retModel.setMsg("词条不能为空！");
			return retModel;
		} else {// 校验词条是否存在
			Lexicons lexicons = new Lexicons(word);
			lexicons = lexiconService.selectOne(lexicons);
			System.out.println("lexicons------------------" + lexicons);
			if (lexicons != null && lexicons.getId() != id) {
				retModel.setFlag(false);
				retModel.setMsg("词条:'" + word + "'已存在！");
				return retModel;
			}
		}

		if (retModel.isFlag()) {
			try {
				Lexicons lexicons = new Lexicons();
				lexicons.setId(id);
				lexicons.setWord(word);
				lexicons.setPinyin(pinyin);
				lexicons.setPos(pos);
				lexicons.setSynonym(synonym);
				// 更新数据库
				int result = lexiconService.update(lexicons);
				if(result > 0){
					retModel.setMsg("修改成功！");
				}else {
					retModel.setFlag(false);
					retModel.setMsg("修改失败！");
					return retModel;
				}
			} catch (Exception e) {
				e.printStackTrace();
				retModel.setFlag(false);
				retModel.setMsg("修改失败！");
				return retModel;
			}
		}
		return retModel;
	}

	@RequestMapping(value = "get", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public @ResponseBody String paginationDataTables(HttpServletRequest request)
			throws IOException {

		// 获取查询关键参数
		String aoData = request.getParameter("aoData");
		// 解析查询关键参数
		PageModel<Lexicons> pageModel = parsePageParamFromJson(aoData);
		// 搜索条件
		String sSearch = pageModel.getsSearch();
		// 总条数(无过滤)
		int total = this.lexiconService.count(new Lexicons());

		// 搜索结果集
		lexiconList = this.lexiconService.selectPage(new Lexicons(sSearch),
				pageModel);
		// 搜索结果数
		pageModel.setiTotalDisplayRecords(total);
		pageModel.setiTotalRecords(total);
		pageModel.setAaData(lexiconList);

		JSONObject json = JSONObject.fromObject(pageModel);
		logger.info("获取词语列表！！！" + json);

		return json.toString();
	}

	// @RequestMapping(value = "/lexicon/get", method = RequestMethod.GET,
	// produces = "application/json;charset=UTF-8")
	// public @ResponseBody String paginationDataTables(HttpServletRequest
	// request) throws IOException {
	//
	// //当前页数
	// String page = request.getParameter("page");
	// logger.info("获取page！！！"+page);
	// //显示条数
	// String rows = request.getParameter("rows");
	// logger.info("获取rows！！！"+rows);
	// //总条数
	// int total = this.lexiconService.count(new Lexicons());
	//
	// //搜索结果集
	// lexiconList = this.lexiconService.select(new Lexicons());
	//
	// JSONObject json = JSONObject.fromObject(lexiconList);
	// logger.info("获取词语列表！！！"+json);
	//
	// return json.toString();
	// }

}
