package com.xmcc.service.Impl;

import com.xmcc.common.ResultEnums;
import com.xmcc.common.ResultResponse;
import com.xmcc.dto.ProductCategoryDto;
import com.xmcc.dto.ProductInfoDto;
import com.xmcc.entity.ProductCategory;
import com.xmcc.entity.ProductInfo;
import com.xmcc.repository.ProductCategoryRepository;
import com.xmcc.repository.ProductInfoRepository;
import com.xmcc.service.ProductInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductInfoServiceImpl implements ProductInfoService {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    @Autowired
    private ProductInfoRepository productInfoRepository;
    @Override
    public ResultResponse queryList() {
        System.out.println("11111111111111111111111111");
        //查询所有分类
        List<ProductCategory> all = productCategoryRepository.findAll();
        //通过流遍历转换dto
        List<ProductCategoryDto> productCategoryDtoList = all.stream().map(productCategory -> ProductCategoryDto.build(productCategory)).collect(Collectors.toList());

        //判断是否为空
        if(CollectionUtils.isEmpty(all)){
            return ResultResponse.fail();
        }
        System.out.println("222222222222222222222222222222222");
        //获取类目编号集合
        List<Integer> typeList = productCategoryDtoList.stream().map(productCategoryDto -> productCategoryDto.getCategoryType()).collect(Collectors.toList());
        //根据typeList查询商品列表

        List<ProductInfo> productInfoList = productInfoRepository.findProductInfoByProductStatusAndCategoryTypeIn(ResultEnums.PRODUCT_UP.getCode(), typeList);

        //对目标集合productCategoryDtoList进行流遍历 取出每个商品的类目编号 设置到对应的目录中
           //将productInfo设置到foods中
        //过滤：不同的type进行不同的封装
        //将productInfo转成Dto类型
        List<ProductCategoryDto> productCategoryDtos= productCategoryDtoList.parallelStream().map(productCategoryDto -> {
    productCategoryDto.setProductInfoDtoList(productInfoList.stream()
    .filter(productInfo -> productInfo.getCategoryType()==productCategoryDto.getCategoryType())
    .map(productInfo -> ProductInfoDto.build(productInfo)).collect(Collectors.toList()));
    return productCategoryDto;
}).collect(Collectors.toList());

        System.out.println("*************"+productCategoryDtos);
        return ResultResponse.success(productCategoryDtos);
    }

    @Override
    public ResultResponse<ProductInfo> quaryById(String productId) {
        if(StringUtils.isBlank(productId)){
            return ResultResponse.fail(ResultEnums.PARAM_ERROR.getMsg());
        }
        Optional<ProductInfo> byId = productInfoRepository.findById(productId);
        if (!byId.isPresent()){
            return ResultResponse.fail(ResultEnums.NOT_EXITS.getMsg());
        }
        ProductInfo productInfo = byId.get();
        if (productInfo.getProductStatus()==ResultEnums.PRODUCT_DOWN.getCode()){
            return ResultResponse.fail(ResultEnums.PRODUCT_DOWN.getMsg());
        }

        return ResultResponse.success(productInfo);
    }

    @Override
    public void updateProduct(ProductInfo productInfo) {
     productInfoRepository.save(productInfo);
    }
}
