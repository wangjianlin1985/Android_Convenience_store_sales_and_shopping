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

    private int payWayId;
    public void setPayWayId(int payWayId) {
        this.payWayId = payWayId;
    }
    public int getPayWayId() {
        return payWayId;
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
    @Resource PayWayDAO payWayDAO;

    /*��������PayWay����*/
    private PayWay payWay;
    public void setPayWay(PayWay payWay) {
        this.payWay = payWay;
    }
    public PayWay getPayWay() {
        return this.payWay;
    }

    /*��ת�����PayWay��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*���PayWay��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddPayWay() {
        ActionContext ctx = ActionContext.getContext();
        try {
            payWayDAO.AddPayWay(payWay);
            ctx.put("message",  java.net.URLEncoder.encode("PayWay��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("PayWay���ʧ��!"));
            return "error";
        }
    }

    /*��ѯPayWay��Ϣ*/
    public String QueryPayWay() {
        if(currentPage == 0) currentPage = 1;
        List<PayWay> payWayList = payWayDAO.QueryPayWayInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        payWayDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = payWayDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = payWayDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("payWayList",  payWayList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryPayWayOutputToExcel() { 
        List<PayWay> payWayList = payWayDAO.QueryPayWayInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "PayWay��Ϣ��¼"; 
        String[] headers = { "֧����ʽid","֧����ʽ����"};
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
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"PayWay.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯPayWay��Ϣ*/
    public String FrontQueryPayWay() {
        if(currentPage == 0) currentPage = 1;
        List<PayWay> payWayList = payWayDAO.QueryPayWayInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        payWayDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = payWayDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = payWayDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("payWayList",  payWayList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�PayWay��Ϣ*/
    public String ModifyPayWayQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������payWayId��ȡPayWay����*/
        PayWay payWay = payWayDAO.GetPayWayByPayWayId(payWayId);

        ctx.put("payWay",  payWay);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�PayWay��Ϣ*/
    public String FrontShowPayWayQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������payWayId��ȡPayWay����*/
        PayWay payWay = payWayDAO.GetPayWayByPayWayId(payWayId);

        ctx.put("payWay",  payWay);
        return "front_show_view";
    }

    /*�����޸�PayWay��Ϣ*/
    public String ModifyPayWay() {
        ActionContext ctx = ActionContext.getContext();
        try {
            payWayDAO.UpdatePayWay(payWay);
            ctx.put("message",  java.net.URLEncoder.encode("PayWay��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("PayWay��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��PayWay��Ϣ*/
    public String DeletePayWay() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            payWayDAO.DeletePayWay(payWayId);
            ctx.put("message",  java.net.URLEncoder.encode("PayWayɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("PayWayɾ��ʧ��!"));
            return "error";
        }
    }

}
