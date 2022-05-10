package com.walnut.cloud.open.common.service.wechat;


import com.walnut.cloud.open.common.bean.wechat.ocr.*;
import com.walnut.cloud.open.common.error.wechat.WxErrorException;

import java.io.File;

/**
 * 基于小程序或 H5 的身份证、银行卡、行驶证 OCR 识别.
 * https://mp.weixin.qq.com/wiki?t=resource/res_main&id=21516712284rHWMX
 *
 */
public interface WxOcrService {

  /**
   * 身份证OCR识别接口.
   *
   * @param imgUrl  图片url地址
   * @return WxMpOcrIdCardResult
   * @throws WxErrorException .
   */
  WxOcrIdCardResult idCard(String imgUrl) throws WxErrorException;

  /**
   * 身份证OCR识别接口.
   *
   * @param imgFile 图片文件对象
   * @return WxMpOcrIdCardResult
   * @throws WxErrorException .
   */
  WxOcrIdCardResult idCard(File imgFile) throws WxErrorException;

  /**
   * 银行卡OCR识别接口
   * 文件大小限制：小于2M
   * @param imgUrl 图片url地址
   * @return WxMpOcrBankCardResult
   * @throws WxErrorException .
   */
  WxOcrBankCardResult bankCard(String imgUrl) throws WxErrorException;

  /**
   * 银行卡OCR识别接口
   * 文件大小限制：小于2M
   * @param imgFile 图片文件对象
   * @return WxMpOcrBankCardResult
   * @throws WxErrorException .
   */
  WxOcrBankCardResult bankCard(File imgFile) throws WxErrorException;

  /**
   * 行驶证OCR识别接口
   * 文件大小限制：小于2M
   * @param imgUrl 图片url地址
   * @return WxMpOcrDrivingResult
   * @throws WxErrorException .
   */
  WxOcrDrivingResult driving(String imgUrl) throws WxErrorException;

  /**
   * 行驶证OCR识别接口
   * 文件大小限制：小于2M
   * @param imgFile 图片文件对象
   * @return WxMpOcrDrivingResult
   * @throws WxErrorException .
   */
  WxOcrDrivingResult driving(File imgFile) throws WxErrorException;

  /**
   * 驾驶证OCR识别接口
   * 文件大小限制：小于2M
   * @param imgUrl 图片url地址
   * @return WxMpOcrDrivingLicenseResult
   * @throws WxErrorException .
   */
  WxOcrDrivingLicenseResult drivingLicense(String imgUrl) throws WxErrorException;

  /**
   * 驾驶证OCR识别接口
   * 文件大小限制：小于2M
   * @param imgFile 图片文件对象
   * @return WxMpOcrDrivingLicenseResult
   * @throws WxErrorException .
   */
  WxOcrDrivingLicenseResult drivingLicense(File imgFile) throws WxErrorException;

  /**
   * 营业执照OCR识别接口
   * 文件大小限制：小于2M
   * @param imgUrl 图片url地址
   * @return WxMpOcrBizLicenseResult
   * @throws WxErrorException .
   */
  WxOcrBizLicenseResult bizLicense(String imgUrl) throws WxErrorException;

  /**
   * 营业执照OCR识别接口
   * 文件大小限制：小于2M
   * @param imgFile 图片文件对象
   * @return WxMpOcrBizLicenseResult
   * @throws WxErrorException .
   */
  WxOcrBizLicenseResult bizLicense(File imgFile) throws WxErrorException;

  /**
   * 通用印刷体OCR识别接口
   * 文件大小限制：小于2M
   * 适用于屏幕截图、印刷体照片等场景
   * @param imgUrl 图片url地址
   * @return WxMpOcrCommResult
   * @throws WxErrorException .
   */
  WxOcrCommResult comm(String imgUrl) throws WxErrorException;

  /**
   * 通用印刷体OCR识别接口
   * 文件大小限制：小于2M
   * 适用于屏幕截图、印刷体照片等场景
   * @param imgFile 图片文件对象
   * @return WxMpOcrCommResult
   * @throws WxErrorException .
   */
  WxOcrCommResult comm(File imgFile) throws WxErrorException;
}
