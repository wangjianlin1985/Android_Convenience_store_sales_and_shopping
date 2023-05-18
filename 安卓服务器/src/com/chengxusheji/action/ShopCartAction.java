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
import com.chengxusheji.dao.ShopCartDAO;
import com.chengxusheji.domain.ShopCart;
import com.chengxusheji.dao.ProductDAO;
import com.chengxusheji.domain.Product;
import com.chengxusheji.dao.UserInfoDAO;
import com.chengxusheji.domain.UserInfo;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class ShopCartAction extends BaseAction {

    /*�������Ҫ��ѯ������: ��Ʒ*/
    private Product productObj;
    public void setProductObj(Product productObj) {
        this.productObj = productObj;
    }
    public Product getProductObj() {
        return this.productObj;
    }

    /*�������Ҫ��ѯ������: �û�*/
    private UserInfo userObj;
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }
    public UserInfo getUserObj() {
        return this.userObj;
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

    private int cartId;
    public void setCartId(int cartId) {
        this.cartId = cartId;
    }
    public int getCartId() {
        return cartId;
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
    @Resource ProductDAO productDAO;
    @Resource UserInfoDAO userInfoDAO;
    @Resource ShopCartDAO shopCartDAO;

    /*��������ShopCart����*/
    private ShopCart shopCart;
    public void setShopCart(ShopCart shopCart) {
        this.shopCart = shopCart;
    }
    public ShopCart getShopCart() {
        return this.shopCart;
    }

    /*��ת�����ShopCart��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�Product��Ϣ*/
        List<Product> productList = productDAO.QueryAllProductInfo();
        ctx.put("productList", productList);
        /*��ѯ���е�UserInfo��Ϣ*/
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        return "add_view";
    }

    /*���ShopCart��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddShopCart() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Product productObj = productDAO.GetProductByProductId(shopCart.getProductObj().getProductId());
            shopCart.setProductObj(productObj);
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(shopCart.getUserObj().getUser_name());
            shopCart.setUserObj(userObj);
            shopCartDAO.AddShopCart(shopCart);
            ctx.put("message",  java.net.URLEncoder.encode("ShopCart��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("ShopCart���ʧ��!"));
            return "error";
        }
    }

    /*��ѯShopCart��Ϣ*/
    public String QueryShopCart() {
        if(currentPage == 0) currentPage = 1;
        List<ShopCart> shopCartList = shopCartDAO.QueryShopCartInfo(productObj, userObj, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        shopCartDAO.CalculateTotalPageAndRecordNumber(productObj, userObj);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = shopCartDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = shopCartDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("shopCartList",  shopCartList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("productObj", productObj);
        List<Product> productList = productDAO.QueryAllProductInfo();
        ctx.put("productList", productList);
        ctx.put("userObj", userObj);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryShopCartOutputToExcel() { 
        List<ShopCart> shopCartList = shopCartDAO.QueryShopCartInfo(productObj,userObj);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "ShopCart��Ϣ��¼"; 
        String[] headers = { "���ﳵid","��Ʒ","�û�","����","��������"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<shopCartList.size();i++) {
        	ShopCart shopCart = shopCartList.get(i); 
        	dataset.add(new String[]{shopCart.getCartId() + "",shopCart.getProductObj().getProductName(),
shopCart.getUserObj().getName(),
shopCart.getPrice() + "",shopCart.getBuyNum() + ""});
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
			response.setHeader("Content-disposition","attachment; filename="+"ShopCart.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯShopCart��Ϣ*/
    public String FrontQueryShopCart() {
        if(currentPage == 0) currentPage = 1;
        List<ShopCart> shopCartList = shopCartDAO.QueryShopCartInfo(productObj, userObj, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        shopCartDAO.CalculateTotalPageAndRecordNumber(productObj, userObj);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = shopCartDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = shopCartDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("shopCartList",  shopCartList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("productObj", productObj);
        List<Product> productList = productDAO.QueryAllProductInfo();
        ctx.put("productList", productList);
        ctx.put("userObj", userObj);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�ShopCart��Ϣ*/
    public String ModifyShopCartQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������cartId��ȡShopCart����*/
        ShopCart shopCart = shopCartDAO.GetShopCartByCartId(cartId);

        List<Product> productList = productDAO.QueryAllProductInfo();
        ctx.put("productList", productList);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("shopCart",  shopCart);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�ShopCart��Ϣ*/
    public String FrontShowShopCartQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������cartId��ȡShopCart����*/
        ShopCart shopCart = shopCartDAO.GetShopCartByCartId(cartId);

        List<Product> productList = productDAO.QueryAllProductInfo();
        ctx.put("productList", productList);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("shopCart",  shopCart);
        return "front_show_view";
    }

    /*�����޸�ShopCart��Ϣ*/
    public String ModifyShopCart() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Product productObj = productDAO.GetProductByProductId(shopCart.getProductObj().getProductId());
            shopCart.setProductObj(productObj);
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(shopCart.getUserObj().getUser_name());
            shopCart.setUserObj(userObj);
            shopCartDAO.UpdateShopCart(shopCart);
            ctx.put("message",  java.net.URLEncoder.encode("ShopCart��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("ShopCart��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��ShopCart��Ϣ*/
    public String DeleteShopCart() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            shopCartDAO.DeleteShopCart(cartId);
            ctx.put("message",  java.net.URLEncoder.encode("ShopCartɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("ShopCartɾ��ʧ��!"));
            return "error";
        }
    }

}
