package br.com.srm.client;

import br.com.srm.client.dto.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("estoqueservice")
public interface EstoqueClient {

    @RequestMapping(value = "/v1/products/{barCode}", method = RequestMethod.GET)
    Product findByBarCode(@PathVariable("barCode") String barCode);

    @RequestMapping(value = "/v1/products/{barCode}/subtractAmount", method = RequestMethod.POST)
    void subtractAmount(@PathVariable("barCode") String barCode,
                        @RequestParam("amount") Integer amount);

}
