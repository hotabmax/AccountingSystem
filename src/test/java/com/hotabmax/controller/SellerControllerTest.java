package com.hotabmax.controller;

import com.hotabmax.controller.keygenerator.ClassOfKey;
import com.hotabmax.filters.resultForFilterAutorization.ResultForFilterAutorization;
import com.hotabmax.models.Product;
import com.hotabmax.models.Sort;
import com.hotabmax.models.User;
import com.hotabmax.servicesJPA.*;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.Cookie;

import java.security.Key;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc
class SellerControllerTest {

    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;
    @Autowired
    private ProductService productService;
    @Autowired
    private SortService sortService;
    @Autowired
    private ClassOfKey classOfKey;
    @Autowired
    private HistoryOfSellingService historyOfSellingService;

    private MockMvc mockMvc;
    private Key key;
    private String jws;
    private Cookie cookie;
    private ResultForFilterAutorization resultForFilterAutorization;
    private String result;

    @BeforeEach
    void setup(WebApplicationContext wac) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    void getTablesSorts() throws Exception{
        try{
            key = classOfKey.getKey();
            userService.createUser(new User("????????", "123",(int)roleService.findByName("????????????????").get(0).getId()));
            jws = Jwts.builder().setSubject("????????"+" "+"123").signWith(key).compact();
            cookie = new Cookie("JWT", jws);
            sortService.createSort(new Sort(92194, "????????"));
            mockMvc.perform(MockMvcRequestBuilders.post("/seller/getTableSorts")
                            .cookie(cookie)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().json("[{'name': '????????'}]"));
            userService.deleteByName("????????");
            sortService.deleteByName("????????");
        } catch (Exception exc){
            userService.deleteByName("????????");
            sortService.deleteByName("????????");
        }

    }

    @Test
    void getTableProducts() throws Exception{
        try {
            key = classOfKey.getKey();
            userService.createUser(new User("????????", "123",(int)roleService.findByName("????????????????").get(0).getId()));
            jws = Jwts.builder().setSubject("????????"+" "+"123").signWith(key).compact();
            cookie = new Cookie("JWT", jws);
            sortService.createSort(new Sort(92194, "????????"));
            productService.createProducts(new Product("????????",1,
                    50, 100, 150,
                    "??????????????",(int) sortService.findByName("????????").get(0).getId()));
            mockMvc.perform(MockMvcRequestBuilders.post("/seller/getTableProducts?name=????????")
                            .cookie(cookie)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().json("[{'name': '????????', 'code': 1, 'amount': 50," +
                            "'purchaseprice': 100, 'sellingprice': 150," +
                            " 'description': '??????????????'}]"));
            userService.deleteByName("????????");
            productService.deleteByName("????????");
            sortService.deleteByName("????????");
        } catch (Exception exc){
            userService.deleteByName("????????");
            productService.deleteByName("????????");
            sortService.deleteByName("????????");
        }

    }

    @Test
    void getTableProductsBySort() throws Exception{
        try {
            key = classOfKey.getKey();
            userService.createUser(new User("????????", "123",(int)roleService.findByName("????????????????").get(0).getId()));
            jws = Jwts.builder().setSubject("????????"+" "+"123").signWith(key).compact();
            cookie = new Cookie("JWT", jws);
            sortService.createSort(new Sort(92194, "????????"));
            productService.createProducts(new Product("????????",1,
                    50, 100, 150,
                    "??????????????",(int) sortService.findByName("????????").get(0).getId()));
            mockMvc.perform(MockMvcRequestBuilders.post("/seller/getTableProductsBySort?nameSort=????????")
                            .cookie(cookie)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().json("[{'name': '????????', 'code': 1, 'amount': 50," +
                            "'purchaseprice': 100, 'sellingprice': 150," +
                            " 'description': '??????????????'}]"));
            userService.deleteByName("????????");
            productService.deleteByName("????????");
            sortService.deleteByName("????????");
        } catch (Exception exc){
            userService.deleteByName("????????");
            productService.deleteByName("????????");
            sortService.deleteByName("????????");
        }

    }

    @Test
    void tranzactionDeleteAmount() throws Exception{
        try {
            key = classOfKey.getKey();
            userService.createUser(new User("????????", "123",(int)roleService.findByName("????????????????").get(0).getId()));
            jws = Jwts.builder().setSubject("????????"+" "+"123").signWith(key).compact();
            cookie = new Cookie("JWT", jws);
            sortService.createSort(new Sort(92194, "????????"));
            productService.createProducts(new Product("????????",1,
                    50, 100, 150,
                    "??????????????",(int) sortService.findByName("????????").get(0).getId()));
            mockMvc.perform(MockMvcRequestBuilders.post("/seller/tranzactionDeleteProductAmount?name=????????&amount=10")
                            .cookie(cookie)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().string("?????????????? ?????????? ???????? ?? ???????????????????? - 10"));
            userService.deleteByName("????????");
            productService.deleteByName("????????");
            sortService.deleteByName("????????");
            historyOfSellingService.deleteAll();
        } catch (Exception exc){
            userService.deleteByName("????????");
            productService.deleteByName("????????");
            sortService.deleteByName("????????");
            historyOfSellingService.deleteAll();
        }

    }
}