// TODO : MAKE END TO END TESTING
//package com.wavestone.shop.controler;
//
//import com.wavestone.shop.adapters.rest.orders.OrderCreateDto;
//import com.wavestone.shop.adapters.rest.orders.OrdersController;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(OrdersController.class)
//public class OrderRestControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
////
////    @Autowired
////    private ObjectMapper objectMapper;
//    @Test
//    public void testCreateOrder() throws Exception {
//        OrderCreateDto orderCreateDto = new OrderCreateDto();
//        orderCreateDto.setDescription("testDescription");
//        orderCreateDto.setCustomerEmail("testCustomerEmail");
//        //orderCreateDto.setOrderLines(Arrays.asList(new OrderLineDto(1L,"testOrder",3)));
//        String orderJson = "{\"description\": \"testDescription\"}";//objectMapper.writeValueAsString(orderCreateDto);
//        mockMvc.perform(MockMvcRequestBuilders.post("/orders" ).contentType(MediaType.APPLICATION_JSON).content(orderJson)).andExpect(status().isOk());
//
//    }
//}
