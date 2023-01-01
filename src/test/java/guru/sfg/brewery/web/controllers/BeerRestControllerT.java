package guru.sfg.brewery.web.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class  BeerRestControllerT extends BaseIT{

    @Test
    void deleteBeer() throws Exception {
        mockMvc.perform(delete("/api/v1/beer/97df0c39-98c4-4ae0-b663-453e8e19c311")
                .header("Api-Key", "spring")
                .header("Api-Secret", "guru"))
                .andExpect(status().isOk());

    }
}
