package com.jd.resource.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jd.common.core.domain.AjaxResult;
import com.jd.common.core.domain.R;
import com.jd.common.utils.DateUtils;
import com.jd.resource.constant.Constants;
import com.jd.resource.domain.TPriceRule;
import com.jd.resource.domain.TProduct;
import com.jd.resource.domain.dto.TProductIdDto;
import com.jd.resource.mapper.TPriceRuleMapper;
import com.jd.resource.mapper.TProductMapper;
import com.jd.resource.service.ProductService;
import com.jd.resource.shopify.ShopifyImage;
import com.jd.resource.shopify.ShopifyProduct;
import com.jd.resource.shopify.ShopifyProductSeries;
import com.jd.resource.shopify.ShopifyVariants;
import com.jd.resource.utils.ThreadPoolUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * 产品Service业务层处理
 *
 * @author chenyang
 * @date 2023-03-23
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private TProductMapper tProductMapper;

    @Autowired
    private TPriceRuleMapper priceRuleMapper;

    @Autowired
    private ThreadPoolUtils poolUtils;

    private static final Executor single = Executors.newSingleThreadExecutor();


    @Override
    @Transactional(rollbackFor = Exception.class)
    public synchronized AjaxResult leadingPriceRule(List<String> files) {
        for (String file : files) {
            try (Workbook workbook = new HSSFWorkbook(new FileInputStream(file))) {
                Sheet sheetAt = workbook.getSheetAt(0);
                for (int i = 1; i < sheetAt.getLastRowNum(); i++) {
                    int finalI = i;
                    single.execute(() -> {
                        TPriceRule priceRule = new TPriceRule();
                        Map<String, Object> mapList = new HashMap<>(8);
                        Map<String, Object> map = new HashMap<>(8);
                        Row row = sheetAt.getRow(finalI);
                        System.out.println("starting price rule---------->" + finalI);
                        Cell qty1 = row.getCell(23);
                        Cell unitPrice1 = row.getCell(24);
                        Cell extPrice1 = row.getCell(25);
                        Cell qty2 = row.getCell(26);
                        Cell unitPrice2 = row.getCell(27);
                        Cell extPrice2 = row.getCell(28);
                        Cell qty3 = row.getCell(29);
                        Cell unitPrice3 = row.getCell(30);
                        Cell extPrice3 = row.getCell(31);
                        Cell qty4 = row.getCell(32);
                        Cell unitPrice4 = row.getCell(33);
                        Cell extPrice4 = row.getCell(34);
                        Cell qty5 = row.getCell(35);
                        Cell unitPrice5 = row.getCell(36);
                        Cell extPrice5 = row.getCell(37);
                        Cell qty6 = row.getCell(38);
                        Cell unitPrice6 = row.getCell(39);
                        Cell extPrice6 = row.getCell(40);
                        Cell qty7 = row.getCell(41);
                        Cell unitPrice7 = row.getCell(42);
                        Cell extPrice7 = row.getCell(43);
                        Cell sku = row.getCell(0);
                        Cell imgUrls = row.getCell(1);
                        Cell vendor = row.getCell(2);
                        Cell title = row.getCell(0);
                        Cell category = row.getCell(7);
                        Cell category2 = row.getCell(8);
                        Cell series = row.getCell(10);
                        Cell packages = row.getCell(11);
                        Cell type = row.getCell(13);
                        Cell status = row.getCell(12);
                        Cell bodyHtml = row.getCell(3);
                        Cell bigDescription = row.getCell(46);
                        Cell dataSheets = row.getCell(6);
                        map.put("vendor", vendor.toString());
                        map.put("sku", sku.toString());
                        map.put("category", category.toString());
                        map.put("category2", category2.toString());
                        map.put("series", series.toString());
                        map.put("manufacturerStandardLeadTime", row.getCell(4).toString());
                        map.put("detailedDescription", row.getCell(5).toString());
                        map.put("package", packages.toString());
                        map.put("type", type.toString());
                        map.put("usage", row.getCell(14).toString());
                        map.put("dimensionsOverall", row.getCell(15).toString());
                        map.put("material", row.getCell(16).toString());
                        map.put("features", row.getCell(17).toString());
                        map.put("msl", row.getCell(19).toString());
                        map.put("eccn", row.getCell(21).toString());
                        map.put("otherNames", row.getCell(44).toString());
                        map.put("standardPackage", row.getCell(46).toString());
                        map.put("pcnManufacturerInformation", row.getCell(54).toString());
                        map.put("featuredProduct", row.getCell(55).toString());
                        map.put("cleanerTreatmentType", row.getCell(56).toString());
                        map.put("specifications", row.getCell(57).toString());
                        map.put("quantity", row.getCell(58).toString());
                        map.put("shelfLife", row.getCell(59).toString());
                        map.put("shelfLifeStart", row.getCell(60).toString());
                        map.put("storageRefrigerationTemperature", row.getCell(61).toString());
                        map.put("msdsMaterialSafetyDataSheet", row.getCell(62).toString());
                        map.put("environmentalInformation", row.getCell(63).toString());
                        map.put("baseProductNumber", row.getCell(64).toString());
                        map.put("mountingType", row.getCell(68).toString());
                        map.put("voltageInput", row.getCell(69).toString());
                        mapList.put("product", map);
                        System.out.println("map---------->" + JSON.toJSONString(mapList));
                        String qty1s = qty1.toString();
                        if (" ".equals(qty1s)) {
                            priceRule.setQty1("0");
                        }
                        String qty2s = qty1s.replaceAll(".0", "");
                        priceRule.setQty1(qty2s);
                        priceRule.setUnitprice1(unitPrice1.toString());
                        priceRule.setExtprice1(extPrice1.toString());
                        priceRule.setQty2(qty2.toString());
                        priceRule.setUnitprice2(unitPrice2.toString());
                        priceRule.setExtprice2(extPrice2.toString());
                        priceRule.setQty3(qty3.toString());
                        priceRule.setUnitprice3(unitPrice3.toString());
                        priceRule.setExtprice3(extPrice3.toString());
                        priceRule.setQty4(qty4.toString());
                        priceRule.setUnitprice4(unitPrice4.toString());
                        priceRule.setExtprice4(extPrice4.toString());
                        priceRule.setQty5(qty5.toString());
                        priceRule.setUnitprice5(unitPrice5.toString());
                        priceRule.setExtprice5(extPrice5.toString());
                        priceRule.setQty6(qty6.toString());
                        priceRule.setUnitprice6(unitPrice6.toString());
                        priceRule.setExtprice6(extPrice6.toString());
                        priceRule.setQty7(qty7.toString());
                        priceRule.setUnitprice7(unitPrice7.toString());
                        priceRule.setExtprice7(extPrice7.toString());
                        priceRule.setCreateTime(DateUtils.getNowDate());
                        int rows = priceRuleMapper.insertTPriceRule(priceRule);
                        if (rows > 0) {
                            TPriceRule tPriceRule = new TPriceRule();
                            // 获取价格规则列表
                            List<TPriceRule> priceRuleList = priceRuleMapper.selectTPriceRuleList(tPriceRule);
                            TProduct product = new TProduct();
                            product.setPriceRuleId(priceRuleList.get(0).getId());
                            product.setPriceRule(JSON.toJSONString(priceRuleList.get(0)));
                            product.setTitle(title.toString());
                            product.setProductType(category.toString());
                            product.setVendor(vendor.toString());
                            product.setSku(sku.toString());
                            product.setImages(imgUrls.toString());
                            product.setStatus(status.toString());
                            product.setBigDescription(bigDescription.toString());
                            product.setBodyHtml(bodyHtml.toString());
                            product.setSeries(category.toString() + "/" + category2.toString());
                            product.setDataSheet(dataSheets.toString());
                            List<String> tagsList = new ArrayList<>();
                            tagsList.add(category2.toString());
                            tagsList.add(series.toString());
                            tagsList.add(packages.toString());
                            System.out.println("JSONArray------------->" + JSON.toJSONString(tagsList));
                            String parameter = JSON.toJSONString(mapList);
                            product.setTags(JSON.toJSONString(tagsList));
                            System.out.println("parameter---->" + parameter);
                            product.setProductParameter(parameter);
                            switch (status.toString()) {
                                case Constants.STATUS_DISCONTINUED:
                                case Constants.STATUS_OBSOLETE:
                                    product.setStatus(Constants.STATUS_SHOPIFY_ARCHIVED);
                                    break;
                                case Constants.STATUS_NO_FOR_NEW_DESIGNS:
                                    product.setStatus(Constants.STATUS_SHOPIFY_DRAFT);
                                    break;
                                case Constants.STATUS_ACTIVE:
                                    product.setStatus(Constants.STATUS_SHOPIFY_ACTIVE);
                                    break;
                                default:
                                    break;
                            }
                            tProductMapper.insertTProduct(product);
                            System.out.println("插入成功！");
                        } else {
                            System.out.println("插入失败！");
                        }
                    });
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return AjaxResult.success("success");
    }

    @Override
    public synchronized AjaxResult leadingShopifyProduct() {

        TProduct tproduct = new TProduct();
        List<TProduct> productList = tProductMapper.selectProductList(tproduct);
        single.execute(() -> {
            for (TProduct product : productList) {
                // shopify 产品对象map
                Map<String, Object> mapProducts = new HashMap<>(8);
                // 封装产品对象map
                Map<String, Object> mapProduct = new HashMap<>(8);
                mapProduct.put("body_html", product.getBodyHtml());
                mapProduct.put("handle", product.getHandle());
                String images = product.getImages();
                String https = images.replaceAll("https", ",https");
                String[] split = https.split(",");
                for (String imageUrl : split) {
                    Map<String, Object> map = new HashMap<>(8);
                    List<Object> list = new ArrayList<>();
                    map.put("src", imageUrl);
                    list.add(map);
                    mapProduct.put("images", list);
                }
                Map<String, Object> mapVariant = new HashMap<>(8);
                mapVariant.put("sku", product.getSku());
                String priceRule = product.getPriceRule();
                JSONObject jsonPriceRule = JSONObject.parseObject(priceRule);
                mapVariant.put("price", jsonPriceRule.getString("unitprice1"));
                mapVariant.put("position", jsonPriceRule.getInteger("qty1"));
                List<Object> list = new ArrayList<>();
                list.add(mapVariant);
                mapProduct.put("variants", list);
                mapProduct.put("status", product.getStatus());
                String tag = product.getTags();
                // 去除带,"-"的
                String tags = tag.replaceAll(",\"-\"", "");
                List<String> listTags = JSON.parseArray(tags, String.class);
                System.out.println("lists:----->" + listTags);
                mapProduct.put("tags", listTags);
                mapProduct.put("product_tags", listTags);
                mapProduct.put("title", product.getSku());
                mapProduct.put("vendor", product.getVendor());
                mapProducts.put("product", mapProduct);
                JSONObject jsonObject = new JSONObject(mapProducts);
                String product1 = ShopifyProduct.createProduct(jsonObject.toJSONString());
                JSONObject jsonObject1 = JSONObject.parseObject(product1);
                System.out.println("jsonObject1-------->" + jsonObject1);
                TProduct upProduct = new TProduct();
                System.out.println("product-----id:::" + jsonObject1.getJSONObject("product").getLong("id"));
                Long productId = jsonObject1.getJSONObject("product").getLong("id");
                upProduct.setProductId(productId);
                upProduct.setId(product.getId());
                // 把产品id更新进去
                tProductMapper.updateTProductById(upProduct);
            }
        });
        return AjaxResult.success("success");
    }


    @Override
    public AjaxResult createSeries() {

        TProduct product = new TProduct();
        // 获取产品列
        List<TProduct> productSeriesList = tProductMapper.selectProductSeriesDistinct(product);
        List<String> seriesObj = new ArrayList<>(8);
        for (TProduct productSeries : productSeriesList) {
            String series = productSeries.getSeries();
            seriesObj.add(series);
        }
        //获取产品系列后去重
        Set<String> splitSeries = new HashSet<>(8);
        for (String series : seriesObj) {
            String[] split = series.split("/");
            splitSeries.addAll(Arrays.asList(split));
        }
        System.out.println("hashSet:---->"+splitSeries);
        for (String s : splitSeries) {
            //获取产品id
            List<TProductIdDto> productList = tProductMapper.selectProductIdList(product);
            Map<String, Object> mapSeries = new HashMap<>(8);
            Map<String, Object> seriesMap = new HashMap<>(8);
            seriesMap.put("title", s);
            mapSeries.put("custom_collection", seriesMap);
            seriesMap.put("collects", productList);
            JSONObject jsonSeries = new JSONObject(mapSeries);
            System.out.println("jsonss-->" + jsonSeries);
            ShopifyProductSeries.createProductSeries(jsonSeries.toJSONString());
        }

        return AjaxResult.success();
    }


}
