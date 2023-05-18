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

    /*界面层需要查询的属性: 商家账号*/
    private String sellUserName;
    public void setSellUserName(String sellUserName) {
        this.sellUserName = sellUserName;
    }
    public String getSellUserName() {
        return this.sellUserName;
    }

    /*界面层需要查询的属性: 商家名称*/
    private String sellerName;
    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }
    public String getSellerName() {
        return this.sellerName;
    }

    /*界面层需要查询的属性: 联系电话*/
    private String telephone;
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    public String getTelephone() {
        return this.telephone;
    }

    /*界面层需要查询的属性: 商家地址*/
    private String address;
    public void setAddress(String address) {
        this.address = address;
    }
    public String getAddress() {
        return this.address;
    }

    /*界面层需要查询的属性: 入住时间*/
    private String regTime;
    public void setRegTime(String regTime) {
        this.regTime = regTime;
    }
    public String getRegTime() {
        return this.regTime;
    }

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

    /*当前查询的总记录数目*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*业务层对象*/
    @Resource SellerDAO sellerDAO;

    /*待操作的Seller对象*/
    private Seller seller;
    public void setSeller(Seller seller) {
        this.seller = seller;
    }
    public Seller getSeller() {
        return this.seller;
    }

    /*跳转到添加Seller视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*添加Seller信息*/
    @SuppressWarnings("deprecation")
    public String AddSeller() {
        ActionContext ctx = ActionContext.getContext();
        /*验证商家账号是否已经存在*/
        String sellUserName = seller.getSellUserName();
        Seller db_seller = sellerDAO.GetSellerBySellUserName(sellUserName);
        if(null != db_seller) {
            ctx.put("error",  java.net.URLEncoder.encode("该商家账号已经存在!"));
            return "error";
        }
        try {
            sellerDAO.AddSeller(seller);
            ctx.put("message",  java.net.URLEncoder.encode("Seller添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Seller添加失败!"));
            return "error";
        }
    }

    /*查询Seller信息*/
    public String QuerySeller() {
        if(currentPage == 0) currentPage = 1;
        if(sellUserName == null) sellUserName = "";
        if(sellerName == null) sellerName = "";
        if(telephone == null) telephone = "";
        if(address == null) address = "";
        if(regTime == null) regTime = "";
        List<Seller> sellerList = sellerDAO.QuerySellerInfo(sellUserName, sellerName, telephone, address, regTime, currentPage);
        /*计算总的页数和总的记录数*/
        sellerDAO.CalculateTotalPageAndRecordNumber(sellUserName, sellerName, telephone, address, regTime);
        /*获取到总的页码数目*/
        totalPage = sellerDAO.getTotalPage();
        /*当前查询条件下总记录数*/
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

    /*后台导出到excel*/
    public String QuerySellerOutputToExcel() { 
        if(sellUserName == null) sellUserName = "";
        if(sellerName == null) sellerName = "";
        if(telephone == null) telephone = "";
        if(address == null) address = "";
        if(regTime == null) regTime = "";
        List<Seller> sellerList = sellerDAO.QuerySellerInfo(sellUserName,sellerName,telephone,address,regTime);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Seller信息记录"; 
        String[] headers = { "商家账号","商家名称","联系电话","入住时间"};
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
		HttpServletResponse response = null;//创建一个HttpServletResponse对象 
		OutputStream out = null;//创建一个输出流对象 
		try { 
			response = ServletActionContext.getResponse();//初始化HttpServletResponse对象 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"Seller.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询Seller信息*/
    public String FrontQuerySeller() {
        if(currentPage == 0) currentPage = 1;
        if(sellUserName == null) sellUserName = "";
        if(sellerName == null) sellerName = "";
        if(telephone == null) telephone = "";
        if(address == null) address = "";
        if(regTime == null) regTime = "";
        List<Seller> sellerList = sellerDAO.QuerySellerInfo(sellUserName, sellerName, telephone, address, regTime, currentPage);
        /*计算总的页数和总的记录数*/
        sellerDAO.CalculateTotalPageAndRecordNumber(sellUserName, sellerName, telephone, address, regTime);
        /*获取到总的页码数目*/
        totalPage = sellerDAO.getTotalPage();
        /*当前查询条件下总记录数*/
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

    /*查询要修改的Seller信息*/
    public String ModifySellerQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键sellUserName获取Seller对象*/
        Seller seller = sellerDAO.GetSellerBySellUserName(sellUserName);

        ctx.put("seller",  seller);
        return "modify_view";
    }

    /*查询要修改的Seller信息*/
    public String FrontShowSellerQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键sellUserName获取Seller对象*/
        Seller seller = sellerDAO.GetSellerBySellUserName(sellUserName);

        ctx.put("seller",  seller);
        return "front_show_view";
    }

    /*更新修改Seller信息*/
    public String ModifySeller() {
        ActionContext ctx = ActionContext.getContext();
        try {
            sellerDAO.UpdateSeller(seller);
            ctx.put("message",  java.net.URLEncoder.encode("Seller信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Seller信息更新失败!"));
            return "error";
       }
   }

    /*删除Seller信息*/
    public String DeleteSeller() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            sellerDAO.DeleteSeller(sellUserName);
            ctx.put("message",  java.net.URLEncoder.encode("Seller删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Seller删除失败!"));
            return "error";
        }
    }

}
