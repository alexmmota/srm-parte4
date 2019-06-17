package br.com.srm.client;

import br.com.srm.client.dto.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("estoqueservice")
public interface EstoqueClient {

    @RequestMapping(value = "/v1/departments/{departmentId}/products/{isbn}",
            method = RequestMethod.GET)
    Product findByIsbn(@PathVariable("departmentId") Long departmentId,
                       @PathVariable("isbn") String isbn);

    @RequestMapping(value = "/v1/departments/{departmentId}/products/{isbn}/subtractAmount",
            method = RequestMethod.POST)
    void subtractAmount(@PathVariable("departmentId") Long departmentId,
                        @PathVariable("isbn") String isbn,
                        @RequestParam("amount") Integer amount);

}
