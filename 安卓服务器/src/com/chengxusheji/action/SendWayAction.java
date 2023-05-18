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
import com.chengxusheji.dao.SendWayDAO;
import com.chengxusheji.domain.SendWay;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class SendWayAction extends BaseAction {

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

    private int sendWayId;
    public void setSendWayId(int sendWayId) {
        this.sendWayId = sendWayId;
    }
    public int getSendWayId() {
        return sendWayId;
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
    @Resource SendWayDAO sendWayDAO;

    /*待操作的SendWay对象*/
    private SendWay sendWay;
    public void setSendWay(SendWay sendWay) {
        this.sendWay = sendWay;
    }
    public SendWay getSendWay() {
        return this.sendWay;
    }

    /*跳转到添加SendWay视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*添加SendWay信息*/
    @SuppressWarnings("deprecation")
    public String AddSendWay() {
        ActionContext ctx = ActionContext.getContext();
        try {
            sendWayDAO.AddSendWay(sendWay);
            ctx.put("message",  java.net.URLEncoder.encode("SendWay添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("SendWay添加失败!"));
            return "error";
        }
    }

    /*查询SendWay信息*/
    public String QuerySendWay() {
        if(currentPage == 0) currentPage = 1;
        List<SendWay> sendWayList = sendWayDAO.QuerySendWayInfo(currentPage);
        /*计算总的页数和总的记录数*/
        sendWayDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = sendWayDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = sendWayDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("sendWayList",  sendWayList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QuerySendWayOutputToExcel() { 
        List<SendWay> sendWayList = sendWayDAO.QuerySendWayInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "SendWay信息记录"; 
        String[] headers = { "送货方式id","送货方式名称"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<sendWayList.size();i++) {
        	SendWay sendWay = sendWayList.get(i); 
        	dataset.add(new String[]{sendWay.getSendWayId() + "",sendWay.getSendWayName()});
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
			response.setHeader("Content-disposition","attachment; filename="+"SendWay.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询SendWay信息*/
    public String FrontQuerySendWay() {
        if(currentPage == 0) currentPage = 1;
        List<SendWay> sendWayList = sendWayDAO.QuerySendWayInfo(currentPage);
        /*计算总的页数和总的记录数*/
        sendWayDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = sendWayDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = sendWayDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("sendWayList",  sendWayList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*查询要修改的SendWay信息*/
    public String ModifySendWayQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键sendWayId获取SendWay对象*/
        SendWay sendWay = sendWayDAO.GetSendWayBySendWayId(sendWayId);

        ctx.put("sendWay",  sendWay);
        return "modify_view";
    }

    /*查询要修改的SendWay信息*/
    public String FrontShowSendWayQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键sendWayId获取SendWay对象*/
        SendWay sendWay = sendWayDAO.GetSendWayBySendWayId(sendWayId);

        ctx.put("sendWay",  sendWay);
        return "front_show_view";
    }

    /*更新修改SendWay信息*/
    public String ModifySendWay() {
        ActionContext ctx = ActionContext.getContext();
        try {
            sendWayDAO.UpdateSendWay(sendWay);
            ctx.put("message",  java.net.URLEncoder.encode("SendWay信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("SendWay信息更新失败!"));
            return "error";
       }
   }

    /*删除SendWay信息*/
    public String DeleteSendWay() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            sendWayDAO.DeleteSendWay(sendWayId);
            ctx.put("message",  java.net.URLEncoder.encode("SendWay删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("SendWay删除失败!"));
            return "error";
        }
    }

}
