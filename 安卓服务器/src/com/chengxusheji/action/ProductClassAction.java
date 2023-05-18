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
import com.chengxusheji.dao.ProductClassDAO;
import com.chengxusheji.domain.ProductClass;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class ProductClassAction extends BaseAction {

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

    private int classId;
    public void setClassId(int classId) {
        this.classId = classId;
    }
    public int getClassId() {
        return classId;
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
    @Resource ProductClassDAO productClassDAO;

    /*��������ProductClass����*/
    private ProductClass productClass;
    public void setProductClass(ProductClass productClass) {
        this.productClass = productClass;
    }
    public ProductClass getProductClass() {
        return this.productClass;
    }

    /*��ת�����ProductClass��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*���ProductClass��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddProductClass() {
        ActionContext ctx = ActionContext.getContext();
        try {
            productClassDAO.AddProductClass(productClass);
            ctx.put("message",  java.net.URLEncoder.encode("ProductClass��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("ProductClass���ʧ��!"));
            return "error";
        }
    }

    /*��ѯProductClass��Ϣ*/
    public String QueryProductClass() {
        if(currentPage == 0) currentPage = 1;
        List<ProductClass> productClassList = productClassDAO.QueryProductClassInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        productClassDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = productClassDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = productClassDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("productClassList",  productClassList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryProductClassOutputToExcel() { 
        List<ProductClass> productClassList = productClassDAO.QueryProductClassInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "ProductClass��Ϣ��¼"; 
        String[] headers = { "���id","�������","�������"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<productClassList.size();i++) {
        	ProductClass productClass = productClassList.get(i); 
        	dataset.add(new String[]{productClass.getClassId() + "",productClass.getClassName(),productClass.getClassDesc()});
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
			response.setHeader("Content-disposition","attachment; filename="+"ProductClass.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯProductClass��Ϣ*/
    public String FrontQueryProductClass() {
        if(currentPage == 0) currentPage = 1;
        List<ProductClass> productClassList = productClassDAO.QueryProductClassInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        productClassDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = productClassDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = productClassDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("productClassList",  productClassList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�ProductClass��Ϣ*/
    public String ModifyProductClassQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������classId��ȡProductClass����*/
        ProductClass productClass = productClassDAO.GetProductClassByClassId(classId);

        ctx.put("productClass",  productClass);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�ProductClass��Ϣ*/
    public String FrontShowProductClassQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������classId��ȡProductClass����*/
        ProductClass productClass = productClassDAO.GetProductClassByClassId(classId);

        ctx.put("productClass",  productClass);
        return "front_show_view";
    }

    /*�����޸�ProductClass��Ϣ*/
    public String ModifyProductClass() {
        ActionContext ctx = ActionContext.getContext();
        try {
            productClassDAO.UpdateProductClass(productClass);
            ctx.put("message",  java.net.URLEncoder.encode("ProductClass��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("ProductClass��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��ProductClass��Ϣ*/
    public String DeleteProductClass() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            productClassDAO.DeleteProductClass(classId);
            ctx.put("message",  java.net.URLEncoder.encode("ProductClassɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("ProductClassɾ��ʧ��!"));
            return "error";
        }
    }

}
