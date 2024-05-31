package com.photon.product.api;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.photon.core.BaseUnitTest;
import com.photon.infrastructure.Response;
import com.photon.product.request.CreateProductRequest;
import com.photon.product.services.ProductCommandService;
import com.photon.product.services.ProductQueryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockPart;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;

@WebMvcTest(ProductApiResource.class)
public class ProductApiResourceTest extends BaseUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductCommandService productCommandService;

    @MockBean
    private ProductQueryService productQueryService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("should return hello world")
    public void shouldReturnHelloWorld() throws Exception {
        //act
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/products/helloWorld")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE);
        MockHttpServletResponse mockResponse = this.mockMvc.perform(requestBuilder)
                .andReturn().getResponse();

        //assert
        System.out.println("hello world");
        assertThat(mockResponse.getStatus()).isEqualTo(200);

    }

    @Test
    @DisplayName("Save product and return 200 successfully")
    public void shouldSaveProduct() throws Exception {

        //arrange
        CreateProductRequest createProductRequest = mock(CreateProductRequest.class);
        Response response = Response.of(UUID.fromString("6cc63550-f135-43fd-8b2b-cdf301417ff1"));
        Mockito.when(productCommandService.saveProduct(Mockito.any(), Mockito.any()))
                .thenReturn(response);

        //act
        String body = "{\"name\":\"pencil\",\"price\":\"200\"}";
        MockPart bodyPart = new MockPart("createProductRequest", objectMapper.writeValueAsString(createProductRequest).getBytes(UTF_8));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .multipart(HttpMethod.POST, "/products")
                .file("file", new byte[0])
                //.file(file)
                //.part(bodyPart)
                //.file(mock(MockMultipartFile.class))
                //.part()
                //.requestAttr("createProductRequest", objectMapper.writeValueAsString(createProductRequest))
                .content(body)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .accept(MediaType.APPLICATION_JSON_VALUE);
        MockHttpServletResponse mockResponse = this.mockMvc.perform(requestBuilder)
                .andReturn().getResponse();

        //assert
        assertThat(mockResponse.getStatus()).isEqualTo(HttpStatus.OK.value());
    }
}
