package org.forest.api.client.product.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "forest-api-server")
public interface ProductRemote {

  @RequestMapping("/queryMerchantProduct")
  public Object queryMerchantProduct(@RequestParam(value = "merchantProductId") Integer merchantProductId);
  
  
}
