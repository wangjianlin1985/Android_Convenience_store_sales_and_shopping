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
import com.chengxusheji.dao.SellerDAO;
import com.chengxusheji.domain.Seller;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class SellerAction extends BaseAction {

    /*�������Ҫ��ѯ������: �̼��˺�*/
    private String sellUserName;
    public void setSellUserName(String sellUserName) {
        this.sellUserName = sellUserName;
    }
    public String getSellUserName() {
        return this.sellUserName;
    }

    /*�������Ҫ��ѯ������: �̼�����*/
    private String sellerName;
    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }
    public String getSellerName() {
        return this.sellerName;
    }

    /*�������Ҫ��ѯ������: ��ϵ�绰*/
    private String telephone;
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    public String getTelephone() {
        return this.telephone;
    }

    /*�������Ҫ��ѯ������: �̼ҵ�ַ*/
    private String address;
    public void setAddress(String address) {
        this.address = address;
    }
    public String getAddress() {
        return this.address;
    }

    /*�������Ҫ��ѯ������: ��סʱ��*/
    private String regTime;
    public void setRegTime(String regTime) {
        this.regTime = regTime;
    }
    public String getRegTime() {
        return this.regTime;
    }

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

    /*��ǰ��ѯ���ܼ�¼��Ŀ*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*ҵ������*/
    @Resource SellerDAO sellerDAO;

    /*��������Seller����*/
    private Seller seller;
    public void setSeller(Seller seller) {
        this.seller = seller;
    }
    public Seller getSeller() {
        return this.seller;
    }

    /*��ת�����Seller��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*���Seller��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddSeller() {
        ActionContext ctx = ActionContext.getContext();
        /*��֤�̼��˺��Ƿ��Ѿ�����*/
        String sellUserName = seller.getSellUserName();
        Seller db_seller = sellerDAO.GetSellerBySellUserName(sellUserName);
        if(null != db_seller) {
            ctx.put("error",  java.net.URLEncoder.encode("���̼��˺��Ѿ�����!"));
            return "error";
        }
        try {
            sellerDAO.AddSeller(seller);
            ctx.put("message",  java.net.URLEncoder.encode("Seller��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Seller���ʧ��!"));
            return "error";
        }
    }

    /*��ѯSeller��Ϣ*/
    public String QuerySeller() {
        if(currentPage == 0) currentPage = 1;
        if(sellUserName == null) sellUserName = "";
        if(sellerName == null) sellerName = "";
        if(telephone == null) telephone = "";
        if(address == null) address = "";
        if(regTime == null) regTime = "";
        List<Seller> sellerList = sellerDAO.QuerySellerInfo(sellUserName, sellerName, telephone, address, regTime, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        sellerDAO.CalculateTotalPageAndRecordNumber(sellUserName, sellerName, telephone, address, regTime);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = sellerDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = sellerDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("sellerList",  sellerList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("sellUserName", sellUserName);
        ctx.put("sellerName", sellerName);
        ctx.put("telephone", telephone);
        ctx.put("address", address);
        ctx.put("regTime", regTime);
        return "query_view";
    }

    /*��̨������excel*/
    public String QuerySellerOutputToExcel() { 
        if(sellUserName == null) sellUserName = "";
        if(sellerName == null) sellerName = "";
        if(telephone == null) telephone = "";
        if(address == null) address = "";
        if(regTime == null) regTime = "";
        List<Seller> sellerList = sellerDAO.QuerySellerInfo(sellUserName,sellerName,telephone,address,regTime);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Seller��Ϣ��¼"; 
        String[] headers = { "�̼��˺�","�̼�����","��ϵ�绰","��סʱ��"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<sellerList.size();i++) {
        	Seller seller = sellerList.get(i); 
        	dataset.add(new String[]{seller.getSellUserName(),seller.getSellerName(),seller.getTelephone(),seller.getRegTime()});
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
			response.setHeader("Content-disposition","attachment; filename="+"Seller.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯSeller��Ϣ*/
    public String FrontQuerySeller() {
        if(currentPage == 0) currentPage = 1;
        if(sellUserName == null) sellUserName = "";
        if(sellerName == null) sellerName = "";
        if(telephone == null) telephone = "";
        if(address == null) address = "";
        if(regTime == null) regTime = "";
        List<Seller> sellerList = sellerDAO.QuerySellerInfo(sellUserName, sellerName, telephone, address, regTime, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        sellerDAO.CalculateTotalPageAndRecordNumber(sellUserName, sellerName, telephone, address, regTime);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = sellerDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = sellerDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("sellerList",  sellerList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("sellUserName", sellUserName);
        ctx.put("sellerName", sellerName);
        ctx.put("telephone", telephone);
        ctx.put("address", address);
        ctx.put("regTime", regTime);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�Seller��Ϣ*/
    public String ModifySellerQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������sellUserName��ȡSeller����*/
        Seller seller = sellerDAO.GetSellerBySellUserName(sellUserName);

        ctx.put("seller",  seller);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�Seller��Ϣ*/
    public String FrontShowSellerQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������sellUserName��ȡSeller����*/
        Seller seller = sellerDAO.GetSellerBySellUserName(sellUserName);

        ctx.put("seller",  seller);
        return "front_show_view";
    }

    /*�����޸�Seller��Ϣ*/
    public String ModifySeller() {
        ActionContext ctx = ActionContext.getContext();
        try {
            sellerDAO.UpdateSeller(seller);
            ctx.put("message",  java.net.URLEncoder.encode("Seller��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Seller��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��Seller��Ϣ*/
    public String DeleteSeller() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            sellerDAO.DeleteSeller(sellUserName);
            ctx.put("message",  java.net.URLEncoder.encode("Sellerɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Sellerɾ��ʧ��!"));
            return "error";
        }
    }

}
