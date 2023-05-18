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
import com.chengxusheji.dao.OrderItemDAO;
import com.chengxusheji.domain.OrderItem;
import com.chengxusheji.dao.OrderInfoDAO;
import com.chengxusheji.domain.OrderInfo;
import com.chengxusheji.dao.ProductDAO;
import com.chengxusheji.domain.Product;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class OrderItemAction extends BaseAction {

    /*界面层需要查询的属性: 所属订单*/
    private OrderInfo orderObj;
    public void setOrderObj(OrderInfo orderObj) {
        this.orderObj = orderObj;
    }
    public OrderInfo getOrderObj() {
        return this.orderObj;
    }

    /*界面层需要查询的属性: 订单商品*/
    private Product productObj;
    public void setProductObj(Product productObj) {
        this.productObj = productObj;
    }
    public Product getProductObj() {
        return this.productObj;
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

    private int itemId;
    public void setItemId(int itemId) {
        this.itemId = itemId;
    }
    public int getItemId() {
        return itemId;
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
    @Resource OrderInfoDAO orderInfoDAO;
    @Resource ProductDAO productDAO;
    @Resource OrderItemDAO orderItemDAO;

    /*待操作的OrderItem对象*/
    private OrderItem orderItem;
    public void setOrderItem(OrderItem orderItem) {
        this.orderItem = orderItem;
    }
    public OrderItem getOrderItem() {
        return this.orderItem;
    }

    /*跳转到添加OrderItem视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的OrderInfo信息*/
        List<OrderInfo> orderInfoList = orderInfoDAO.QueryAllOrderInfoInfo();
        ctx.put("orderInfoList", orderInfoList);
        /*查询所有的Product信息*/
        List<Product> productList = productDAO.QueryAllProductInfo();
        ctx.put("productList", productList);
        return "add_view";
    }

    /*添加OrderItem信息*/
    @SuppressWarnings("deprecation")
    public String AddOrderItem() {
        ActionContext ctx = ActionContext.getContext();
        try {
            OrderInfo orderObj = orderInfoDAO.GetOrderInfoByOrderNo(orderItem.getOrderObj().getOrderNo());
            orderItem.setOrderObj(orderObj);
            Product productObj = productDAO.GetProductByProductId(orderItem.getProductObj().getProductId());
            orderItem.setProductObj(productObj);
            orderItemDAO.AddOrderItem(orderItem);
            ctx.put("message",  java.net.URLEncoder.encode("OrderItem添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("OrderItem添加失败!"));
            return "error";
        }
    }

    /*查询OrderItem信息*/
    public String QueryOrderItem() {
        if(currentPage == 0) currentPage = 1;
        List<OrderItem> orderItemList = orderItemDAO.QueryOrderItemInfo(orderObj, productObj, currentPage);
        /*计算总的页数和总的记录数*/
        orderItemDAO.CalculateTotalPageAndRecordNumber(orderObj, productObj);
        /*获取到总的页码数目*/
        totalPage = orderItemDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = orderItemDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("orderItemList",  orderItemList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("orderObj", orderObj);
        List<OrderInfo> orderInfoList = orderInfoDAO.QueryAllOrderInfoInfo();
        ctx.put("orderInfoList", orderInfoList);
        ctx.put("productObj", productObj);
        List<Product> productList = productDAO.QueryAllProductInfo();
        ctx.put("productList", productList);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryOrderItemOutputToExcel() { 
        List<OrderItem> orderItemList = orderItemDAO.QueryOrderItemInfo(orderObj,productObj);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "OrderItem信息记录"; 
        String[] headers = { "条目id","所属订单","订单商品","商品单价","购买数量"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<orderItemList.size();i++) {
        	OrderItem orderItem = orderItemList.get(i); 
        	dataset.add(new String[]{orderItem.getItemId() + "",orderItem.getOrderObj().getOrderNo(),
orderItem.getProductObj().getProductName(),
orderItem.getPrice() + "",orderItem.getOrderNumer() + ""});
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
			response.setHeader("Content-disposition","attachment; filename="+"OrderItem.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询OrderItem信息*/
    public String FrontQueryOrderItem() {
        if(currentPage == 0) currentPage = 1;
        List<OrderItem> orderItemList = orderItemDAO.QueryOrderItemInfo(orderObj, productObj, currentPage);
        /*计算总的页数和总的记录数*/
        orderItemDAO.CalculateTotalPageAndRecordNumber(orderObj, productObj);
        /*获取到总的页码数目*/
        totalPage = orderItemDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = orderItemDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("orderItemList",  orderItemList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("orderObj", orderObj);
        List<OrderInfo> orderInfoList = orderInfoDAO.QueryAllOrderInfoInfo();
        ctx.put("orderInfoList", orderInfoList);
        ctx.put("productObj", productObj);
        List<Product> productList = productDAO.QueryAllProductInfo();
        ctx.put("productList", productList);
        return "front_query_view";
    }

    /*查询要修改的OrderItem信息*/
    public String ModifyOrderItemQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键itemId获取OrderItem对象*/
        OrderItem orderItem = orderItemDAO.GetOrderItemByItemId(itemId);

        List<OrderInfo> orderInfoList = orderInfoDAO.QueryAllOrderInfoInfo();
        ctx.put("orderInfoList", orderInfoList);
        List<Product> productList = productDAO.QueryAllProductInfo();
        ctx.put("productList", productList);
        ctx.put("orderItem",  orderItem);
        return "modify_view";
    }

    /*查询要修改的OrderItem信息*/
    public String FrontShowOrderItemQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键itemId获取OrderItem对象*/
        OrderItem orderItem = orderItemDAO.GetOrderItemByItemId(itemId);

        List<OrderInfo> orderInfoList = orderInfoDAO.QueryAllOrderInfoInfo();
        ctx.put("orderInfoList", orderInfoList);
        List<Product> productList = productDAO.QueryAllProductInfo();
        ctx.put("productList", productList);
        ctx.put("orderItem",  orderItem);
        return "front_show_view";
    }

    /*更新修改OrderItem信息*/
    public String ModifyOrderItem() {
        ActionContext ctx = ActionContext.getContext();
        try {
            OrderInfo orderObj = orderInfoDAO.GetOrderInfoByOrderNo(orderItem.getOrderObj().getOrderNo());
            orderItem.setOrderObj(orderObj);
            Product productObj = productDAO.GetProductByProductId(orderItem.getProductObj().getProductId());
            orderItem.setProductObj(productObj);
            orderItemDAO.UpdateOrderItem(orderItem);
            ctx.put("message",  java.net.URLEncoder.encode("OrderItem信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("OrderItem信息更新失败!"));
            return "error";
       }
   }

    /*删除OrderItem信息*/
    public String DeleteOrderItem() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            orderItemDAO.DeleteOrderItem(itemId);
            ctx.put("message",  java.net.URLEncoder.encode("OrderItem删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("OrderItem删除失败!"));
            return "error";
        }
    }

}
