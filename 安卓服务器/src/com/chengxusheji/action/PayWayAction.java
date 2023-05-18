package com.chengxusheji.action;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import com.opensymphony.xwork2.ActionContext;
import com.chengxusheji.dao.PayWayDAO;
import com.chengxusheji.domain.PayWay;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class PayWayAction extends BaseAction {

    /*当前第几页*/
    private int currentPage;
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }

    /*一共多少页*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    private int payWayId;
    public void setPayWayId(int payWayId) {
        this.payWayId = payWayId;
    }
    public int getPayWayId() {
        return payWayId;
    }

    /*当前查询的总记录数目*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*业务层对象*/
    @Resource PayWayDAO payWayDAO;

    /*待操作的PayWay对象*/
    private PayWay payWay;
    public void setPayWay(PayWay payWay) {
        this.payWay = payWay;
    }
    public PayWay getPayWay() {
        return this.payWay;
    }

    /*跳转到添加PayWay视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*添加PayWay信息*/
    @SuppressWarnings("deprecation")
    public String AddPayWay() {
        ActionContext ctx = ActionContext.getContext();
        try {
            payWayDAO.AddPayWay(payWay);
            ctx.put("message",  java.net.URLEncoder.encode("PayWay添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("PayWay添加失败!"));
            return "error";
        }
    }

    /*查询PayWay信息*/
    public String QueryPayWay() {
        if(currentPage == 0) currentPage = 1;
        List<PayWay> payWayList = payWayDAO.QueryPayWayInfo(currentPage);
        /*计算总的页数和总的记录数*/
        payWayDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = payWayDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = payWayDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("payWayList",  payWayList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryPayWayOutputToExcel() { 
        List<PayWay> payWayList = payWayDAO.QueryPayWayInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "PayWay信息记录"; 
        String[] headers = { "支付方式id","支付方式名称"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<payWayList.size();i++) {
        	PayWay payWay = payWayList.get(i); 
        	dataset.add(new String[]{payWay.getPayWayId() + "",payWay.getPayWayName()});
        }
        /*
        OutputStream out = null;
		try {
			out = new FileOutputStream("C://output.xls");
			ex.exportExcel(title,headers, dataset, out);
		    out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		HttpServletResponse response = null;//创建一个HttpServletResponse对象 
		OutputStream out = null;//创建一个输出流对象 
		try { 
			response = ServletActionContext.getResponse();//初始化HttpServletResponse对象 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"PayWay.xls");//filename是下载的xls的名，建议最好用英文 
			response.setContentType("application/msexcel;charset=UTF-8");//设置类型 
			response.setHeader("Pragma","No-cache");//设置头 
			response.setHeader("Cache-Control","no-cache");//设置头 
			response.setDateHeader("Expires", 0);//设置日期头  
			String rootPath = ServletActionContext.getServletContext().getRealPath("/");
			ex.exportExcel(rootPath,title,headers, dataset, out);
			out.flush();
		} catch (IOException e) { 
			e.printStackTrace(); 
		}finally{
			try{
				if(out!=null){ 
					out.close(); 
				}
			}catch(IOException e){ 
				e.printStackTrace(); 
			} 
		}
		return null;
    }
    /*前台查询PayWay信息*/
    public String FrontQueryPayWay() {
        if(currentPage == 0) currentPage = 1;
        List<PayWay> payWayList = payWayDAO.QueryPayWayInfo(currentPage);
        /*计算总的页数和总的记录数*/
        payWayDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = payWayDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = payWayDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("payWayList",  payWayList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*查询要修改的PayWay信息*/
    public String ModifyPayWayQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键payWayId获取PayWay对象*/
        PayWay payWay = payWayDAO.GetPayWayByPayWayId(payWayId);

        ctx.put("payWay",  payWay);
        return "modify_view";
    }

    /*查询要修改的PayWay信息*/
    public String FrontShowPayWayQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键payWayId获取PayWay对象*/
        PayWay payWay = payWayDAO.GetPayWayByPayWayId(payWayId);

        ctx.put("payWay",  payWay);
        return "front_show_view";
    }

    /*更新修改PayWay信息*/
    public String ModifyPayWay() {
        ActionContext ctx = ActionContext.getContext();
        try {
            payWayDAO.UpdatePayWay(payWay);
            ctx.put("message",  java.net.URLEncoder.encode("PayWay信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("PayWay信息更新失败!"));
            return "error";
       }
   }

    /*删除PayWay信息*/
    public String DeletePayWay() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            payWayDAO.DeletePayWay(payWayId);
            ctx.put("message",  java.net.URLEncoder.encode("PayWay删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("PayWay删除失败!"));
            return "error";
        }
    }

}
