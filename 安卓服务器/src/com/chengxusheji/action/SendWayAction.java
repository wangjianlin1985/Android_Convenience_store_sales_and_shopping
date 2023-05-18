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

    /*��ǰ�ڼ�ҳ*/
    private int currentPage;
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }

    /*һ������ҳ*/
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

    /*��ǰ��ѯ���ܼ�¼��Ŀ*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*ҵ������*/
    @Resource SendWayDAO sendWayDAO;

    /*��������SendWay����*/
    private SendWay sendWay;
    public void setSendWay(SendWay sendWay) {
        this.sendWay = sendWay;
    }
    public SendWay getSendWay() {
        return this.sendWay;
    }

    /*��ת�����SendWay��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*���SendWay��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddSendWay() {
        ActionContext ctx = ActionContext.getContext();
        try {
            sendWayDAO.AddSendWay(sendWay);
            ctx.put("message",  java.net.URLEncoder.encode("SendWay��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("SendWay���ʧ��!"));
            return "error";
        }
    }

    /*��ѯSendWay��Ϣ*/
    public String QuerySendWay() {
        if(currentPage == 0) currentPage = 1;
        List<SendWay> sendWayList = sendWayDAO.QuerySendWayInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        sendWayDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = sendWayDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = sendWayDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("sendWayList",  sendWayList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*��̨������excel*/
    public String QuerySendWayOutputToExcel() { 
        List<SendWay> sendWayList = sendWayDAO.QuerySendWayInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "SendWay��Ϣ��¼"; 
        String[] headers = { "�ͻ���ʽid","�ͻ���ʽ����"};
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
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"SendWay.xls");//filename�����ص�xls���������������Ӣ�� 
			response.setContentType("application/msexcel;charset=UTF-8");//�������� 
			response.setHeader("Pragma","No-cache");//����ͷ 
			response.setHeader("Cache-Control","no-cache");//����ͷ 
			response.setDateHeader("Expires", 0);//��������ͷ  
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
    /*ǰ̨��ѯSendWay��Ϣ*/
    public String FrontQuerySendWay() {
        if(currentPage == 0) currentPage = 1;
        List<SendWay> sendWayList = sendWayDAO.QuerySendWayInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        sendWayDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = sendWayDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = sendWayDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("sendWayList",  sendWayList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�SendWay��Ϣ*/
    public String ModifySendWayQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������sendWayId��ȡSendWay����*/
        SendWay sendWay = sendWayDAO.GetSendWayBySendWayId(sendWayId);

        ctx.put("sendWay",  sendWay);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�SendWay��Ϣ*/
    public String FrontShowSendWayQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������sendWayId��ȡSendWay����*/
        SendWay sendWay = sendWayDAO.GetSendWayBySendWayId(sendWayId);

        ctx.put("sendWay",  sendWay);
        return "front_show_view";
    }

    /*�����޸�SendWay��Ϣ*/
    public String ModifySendWay() {
        ActionContext ctx = ActionContext.getContext();
        try {
            sendWayDAO.UpdateSendWay(sendWay);
            ctx.put("message",  java.net.URLEncoder.encode("SendWay��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("SendWay��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��SendWay��Ϣ*/
    public String DeleteSendWay() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            sendWayDAO.DeleteSendWay(sendWayId);
            ctx.put("message",  java.net.URLEncoder.encode("SendWayɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("SendWayɾ��ʧ��!"));
            return "error";
        }
    }

}
