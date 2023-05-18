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

    private int classId;
    public void setClassId(int classId) {
        this.classId = classId;
    }
    public int getClassId() {
        return classId;
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
    @Resource ProductClassDAO productClassDAO;

    /*待操作的ProductClass对象*/
    private ProductClass productClass;
    public void setProductClass(ProductClass productClass) {
        this.productClass = productClass;
    }
    public ProductClass getProductClass() {
        return this.productClass;
    }

    /*跳转到添加ProductClass视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*添加ProductClass信息*/
    @SuppressWarnings("deprecation")
    public String AddProductClass() {
        ActionContext ctx = ActionContext.getContext();
        try {
            productClassDAO.AddProductClass(productClass);
            ctx.put("message",  java.net.URLEncoder.encode("ProductClass添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("ProductClass添加失败!"));
            return "error";
        }
    }

    /*查询ProductClass信息*/
    public String QueryProductClass() {
        if(currentPage == 0) currentPage = 1;
        List<ProductClass> productClassList = productClassDAO.QueryProductClassInfo(currentPage);
        /*计算总的页数和总的记录数*/
        productClassDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = productClassDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = productClassDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("productClassList",  productClassList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryProductClassOutputToExcel() { 
        List<ProductClass> productClassList = productClassDAO.QueryProductClassInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "ProductClass信息记录"; 
        String[] headers = { "类别id","类别名称","类别描述"};
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
		HttpServletResponse response = null;//创建一个HttpServletResponse对象 
		OutputStream out = null;//创建一个输出流对象 
		try { 
			response = ServletActionContext.getResponse();//初始化HttpServletResponse对象 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"ProductClass.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询ProductClass信息*/
    public String FrontQueryProductClass() {
        if(currentPage == 0) currentPage = 1;
        List<ProductClass> productClassList = productClassDAO.QueryProductClassInfo(currentPage);
        /*计算总的页数和总的记录数*/
        productClassDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = productClassDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = productClassDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("productClassList",  productClassList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*查询要修改的ProductClass信息*/
    public String ModifyProductClassQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键classId获取ProductClass对象*/
        ProductClass productClass = productClassDAO.GetProductClassByClassId(classId);

        ctx.put("productClass",  productClass);
        return "modify_view";
    }

    /*查询要修改的ProductClass信息*/
    public String FrontShowProductClassQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键classId获取ProductClass对象*/
        ProductClass productClass = productClassDAO.GetProductClassByClassId(classId);

        ctx.put("productClass",  productClass);
        return "front_show_view";
    }

    /*更新修改ProductClass信息*/
    public String ModifyProductClass() {
        ActionContext ctx = ActionContext.getContext();
        try {
            productClassDAO.UpdateProductClass(productClass);
            ctx.put("message",  java.net.URLEncoder.encode("ProductClass信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("ProductClass信息更新失败!"));
            return "error";
       }
   }

    /*删除ProductClass信息*/
    public String DeleteProductClass() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            productClassDAO.DeleteProductClass(classId);
            ctx.put("message",  java.net.URLEncoder.encode("ProductClass删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("ProductClass删除失败!"));
            return "error";
        }
    }

}
