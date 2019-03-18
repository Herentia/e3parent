package cn.e3mall.item.pojo;

import cn.e3mall.pojo.TbItem;

/**
 * @author haohan
 * 03/18/2019 - 02:42 下午
 */
public class Item extends TbItem {

    public Item(TbItem tbItem) {
        this.setId(tbItem.getId());
        this.setTitle(tbItem.getTitle());
        this.setSellPoint(tbItem.getSellPoint());
        this.setPrice(tbItem.getPrice());
        this.setNum(tbItem.getNum());
        this.setBarcode(tbItem.getBarcode());
        this.setImage(tbItem.getImage());
        this.setCid(tbItem.getCid());
        this.setStatus(tbItem.getStatus());
        this.setCreated(tbItem.getCreated());
        this.setUpdated(tbItem.getUpdated());
    }

    public String[] getImages() {
        String iamge2 = this.getImage();
        if(iamge2 != null && !"".equals(iamge2)) {
            String[] images = iamge2.split(",");
            return images;
        }
        return null;
    }

}
