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
import com.chengxusheji.dao.ProductDAO;
import com.chengxusheji.domain.Product;
import com.chengxusheji.dao.ProductClassDAO;
import com.chengxusheji.domain.ProductClass;
import com.chengxusheji.dao.SellerDAO;
import com.chengxusheji.domain.Seller;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class ProductAction extends BaseAction {

	/*图片或文件字段mainPhoto参数接收*/
	private File mainPhotoFile;
	private String mainPhotoFileFileName;
	private String mainPhotoFileContentType;
	public File getMainPhotoFile() {
		return mainPhotoFile;
	}
	public void setMainPhotoFile(File mainPhotoFile) {
		this.mainPhotoFile = mainPhotoFile;
	}
	public String getMainPhotoFileFileName() {
		return mainPhotoFileFileName;
	}
	public void setMainPhotoFileFileName(String mainPhotoFileFileName) {
		this.mainPhotoFileFileName = mainPhotoFileFileName;
	}
	public String getMainPhotoFileContentType() {
		return mainPhotoFileContentType;
	}
	public void setMainPhotoFileContentType(String mainPhotoFileContentType) {
		this.mainPhotoFileContentType = mainPhotoFileContentType;
	}
    /*界面层需要查询的属性: 商品类别*/
    private ProductClass productClassObj;
    public void setProductClassObj(ProductClass productClassObj) {
        this.productClassObj = productClassObj;
    }
    public ProductClass getProductClassObj() {
        return this.productClassObj;
    }

    /*界面层需要查询的属性: 商品名称*/
    private String productName;
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public String getProductName() {
        return this.productName;
    }

    /*界面层需要查询的属性: 发布商家*/
    private Seller sellerObj;
    public void setSellerObj(Seller sellerObj) {
        this.sellerObj = sellerObj;
    }
    public Seller getSellerObj() {
        return this.sellerObj;
    }

    /*界面层需要查询的属性: 发布时间*/
    private String addTime;
    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }
    public String getAddTime() {
        return this.addTime;
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

    private int productId;
    public void setProductId(int productId) {
        this.productId = productId;
    }
    public int getProductId() {
        return productId;
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
    @Resource SellerDAO sellerDAO;
    @Resource ProductDAO productDAO;

    /*待操作的Product对象*/
    private Product product;
    public void setProduct(Product product) {
        this.product = product;
    }
    public Product getProduct() {
        return this.product;
    }

    /*跳转到添加Product视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的ProductClass信息*/
        List<ProductClass> productClassList = productClassDAO.QueryAllProductClassInfo();
        ctx.put("productClassList", productClassList);
        /*查询所有的Seller信息*/
        List<Seller> sellerList = sellerDAO.QueryAllSellerInfo();
        ctx.put("sellerList", sellerList);
        return "add_view";
    }

    /*添加Product信息*/
    @SuppressWarnings("deprecation")
    public String AddProduct() {
        ActionContext ctx = ActionContext.getContext();
        try {
            ProductClass productClassObj = productClassDAO.GetProductClassByClassId(product.getProductClassObj().getClassId());
            product.setProductClassObj(productClassObj);
            Seller sellerObj = sellerDAO.GetSellerBySellUserName(product.getSellerObj().getSellUserName());
            product.setSellerObj(sellerObj);
            /*处理商品主图上传*/
            String mainPhotoPath = "upload/noimage.jpg"; 
       	 	if(mainPhotoFile != null)
       	 		mainPhotoPath = photoUpload(mainPhotoFile,mainPhotoFileContentType);
       	 	product.setMainPhoto(mainPhotoPath);
            productDAO.AddProduct(product);
            ctx.put("message",  java.net.URLEncoder.encode("Product添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Product添加失败!"));
            return "error";
        }
    }

    /*查询Product信息*/
    public String QueryProduct() {
        if(currentPage == 0) currentPage = 1;
        if(productName == null) productName = "";
        if(addTime == null) addTime = "";
        List<Product> productList = productDAO.QueryProductInfo(productClassObj, productName, sellerObj, addTime, currentPage);
        /*计算总的页数和总的记录数*/
        productDAO.CalculateTotalPageAndRecordNumber(productClassObj, productName, sellerObj, addTime);
        /*获取到总的页码数目*/
        totalPage = productDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = productDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("productList",  productList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("productClassObj", productClassObj);
        List<ProductClass> productClassList = productClassDAO.QueryAllProductClassInfo();
        ctx.put("productClassList", productClassList);
        ctx.put("productName", productName);
        ctx.put("sellerObj", sellerObj);
        List<Seller> sellerList = sellerDAO.QueryAllSellerInfo();
        ctx.put("sellerList", sellerList);
        ctx.put("addTime", addTime);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryProductOutputToExcel() { 
        if(productName == null) productName = "";
        if(addTime == null) addTime = "";
        List<Product> productList = productDAO.QueryProductInfo(productClassObj,productName,sellerObj,addTime);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Product信息记录"; 
        String[] headers = { "商品id","商品类别","商品名称","商品主图","商品价格","发布商家","发布时间"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<productList.size();i++) {
        	Product product = productList.get(i); 
        	dataset.add(new String[]{product.getProductId() + "",product.getProductClassObj().getClassName(),
product.getProductName(),product.getMainPhoto(),product.getPrice() + "",product.getSellerObj().getSellerName(),
product.getAddTime()});
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
			response.setHeader("Content-disposition","attachment; filename="+"Product.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询Product信息*/
    public String FrontQueryProduct() {
        if(currentPage == 0) currentPage = 1;
        if(productName == null) productName = "";
        if(addTime == null) addTime = "";
        List<Product> productList = productDAO.QueryProductInfo(productClassObj, productName, sellerObj, addTime, currentPage);
        /*计算总的页数和总的记录数*/
        productDAO.CalculateTotalPageAndRecordNumber(productClassObj, productName, sellerObj, addTime);
        /*获取到总的页码数目*/
        totalPage = productDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = productDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("productList",  productList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("productClassObj", productClassObj);
        List<ProductClass> productClassList = productClassDAO.QueryAllProductClassInfo();
        ctx.put("productClassList", productClassList);
        ctx.put("productName", productName);
        ctx.put("sellerObj", sellerObj);
        List<Seller> sellerList = sellerDAO.QueryAllSellerInfo();
        ctx.put("sellerList", sellerList);
        ctx.put("addTime", addTime);
        return "front_query_view";
    }

    /*查询要修改的Product信息*/
    public String ModifyProductQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键productId获取Product对象*/
        Product product = productDAO.GetProductByProductId(productId);

        List<ProductClass> productClassList = productClassDAO.QueryAllProductClassInfo();
        ctx.put("productClassList", productClassList);
        List<Seller> sellerList = sellerDAO.QueryAllSellerInfo();
        ctx.put("sellerList", sellerList);
        ctx.put("product",  product);
        return "modify_view";
    }

    /*查询要修改的Product信息*/
    public String FrontShowProductQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键productId获取Product对象*/
        Product product = productDAO.GetProductByProductId(productId);

        List<ProductClass> productClassList = productClassDAO.QueryAllProductClassInfo();
        ctx.put("productClassList", productClassList);
        List<Seller> sellerList = sellerDAO.QueryAllSellerInfo();
        ctx.put("sellerList", sellerList);
        ctx.put("product",  product);
        return "front_show_view";
    }

    /*更新修改Product信息*/
    public String ModifyProduct() {
        ActionContext ctx = ActionContext.getContext();
        try {
            ProductClass productClassObj = productClassDAO.GetProductClassByClassId(product.getProductClassObj().getClassId());
            product.setProductClassObj(productClassObj);
            Seller sellerObj = sellerDAO.GetSellerBySellUserName(product.getSellerObj().getSellUserName());
            product.setSellerObj(sellerObj);
            /*处理商品主图上传*/
            if(mainPhotoFile != null) {
            	String mainPhotoPath = photoUpload(mainPhotoFile,mainPhotoFileContentType);
            	product.setMainPhoto(mainPhotoPath);
            }
            productDAO.UpdateProduct(product);
            ctx.put("message",  java.net.URLEncoder.encode("Product信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Product信息更新失败!"));
            return "error";
       }
   }

    /*删除Product信息*/
    public String DeleteProduct() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            productDAO.DeleteProduct(productId);
            ctx.put("message",  java.net.URLEncoder.encode("Product删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Product删除失败!"));
            return "error";
        }
    }

}
