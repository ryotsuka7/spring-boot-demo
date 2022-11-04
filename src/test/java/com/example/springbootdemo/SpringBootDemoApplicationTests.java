package com.example.springbootdemo;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.springbootdemo.dto.ItemRequest;
import com.example.springbootdemo.dto.ItemResponse;
import com.example.springbootdemo.dto.Sample;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@AutoConfigureMockMvc
class SpringBootDemoApplicationTests {

    // APIを発行するためのMockオブジェクトを生成
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testHello() throws Exception {
        // 検証するAPIパス
        final String API_PATH = "/hello";

        // JavaのObjectをJSONに変換するためのクラスを生成
        ObjectMapper objectMapper = new ObjectMapper();

        // 結果を検証するためのクラスを生成して、期待値をセット
        Sample sample = new Sample();
        sample.setId(100);
        sample.setName("taro");

        // 「/hello」パスのAPIを実行してレスポンスを検証
        this.mockMvc.perform(MockMvcRequestBuilders.get(API_PATH))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(sample)));
    }

    @Test
    @Transactional
    void testCrud() throws Exception {
        // select(idは1)
        /**
         * GETでIDを指定して1件取得するテスト
         */
        // 検証するAPIパス
        final String API_PATH1 = "/items/1";

        // JavaのObjectをJSONに変換するためのクラスを生成
        ObjectMapper objectMapper = new ObjectMapper();

        // 期待値を設定
        ItemResponse itemResponse = new ItemResponse();
        itemResponse.setId(1);
        itemResponse.setItemName("大豆");

        // APIを実行してレスポンスを検証
        this.mockMvc.perform(MockMvcRequestBuilders.get(API_PATH1))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(itemResponse)));

        /**
         * POSTによる登録処理のテスト
         * 2件登録されている前提なので、idは3となる
         */
        final String API_PATH2 = "/items";

        // リクエストボディのデータを設定
        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setItemName("コーヒー豆");

        // 期待値を設定
        itemResponse = new ItemResponse();
        itemResponse.setId(3);
        itemResponse.setItemName("コーヒー豆");

        // APIを実行してレスポンスを検証
        this.mockMvc.perform(
                MockMvcRequestBuilders
                    .post(API_PATH2)
                    .content(objectMapper.writeValueAsString(itemRequest))
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
            )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isCreated())
            .andExpect(content().json(objectMapper.writeValueAsString(itemResponse)));

        /**
         * GETでIDを指定せずに全件を取得するテスト
         */
        // 検証するAPIパス
        final String API_PATH3 = "/items";

        // APIを実行
        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.get(API_PATH3));
        // レスポンスを出力して、ステータスコードを検証
        resultActions
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk());
        // JSONの件数が3件であることを検証
        // リクエストボディを文字列(JSON)をJavaオブジェクトに変換して、Listのサイズをassert
        String contentAsString = resultActions.andReturn().getResponse()
            .getContentAsString(StandardCharsets.UTF_8);
        List<ItemResponse> itemResponseList = objectMapper.readValue(contentAsString,
            new TypeReference<List<ItemResponse>>() {
            });
        assertThat(itemResponseList, hasSize(3));

        /**
         * PUTによる更新処理のテスト
         * id=1のデータの商品名を更新
         */
        final String API_PATH4 = "/items/1";

        // リクエストボディのデータを設定
        itemRequest = new ItemRequest();
        itemRequest.setItemName("茶豆");

        // 期待値を設定
        itemResponse = new ItemResponse();
        itemResponse.setId(1);
        itemResponse.setItemName("茶豆");

        // APIを実行してレスポンスを検証
        this.mockMvc.perform(
                MockMvcRequestBuilders
                    .put(API_PATH4)
                    .content(objectMapper.writeValueAsString(itemRequest))
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
            )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(itemResponse)));

        /**
         * PUTでDB上の値が更新されているか、GETでIDを指定して1件取得して確認
         */
        // 検証するAPIパス
        final String API_PATH5 = "/items/1";

        // 期待値を設定
        itemResponse = new ItemResponse();
        itemResponse.setId(1);
        itemResponse.setItemName("茶豆");

        // APIを実行してレスポンスを検証
        this.mockMvc.perform(MockMvcRequestBuilders.get(API_PATH1))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(itemResponse)));

        /**
         * DELETEによる更新処理のテスト
         * id=2のデータを削除
         */
        // 検証するAPIパス
        final String API_PATH6 = "/items/2";

        // APIを実行してレスポンスを検証
        this.mockMvc.perform(MockMvcRequestBuilders.delete(API_PATH6))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isNoContent());

        /**
         * DELETEでDB上のレコードが削除されているか、GETで全件取得して件数で確認
         */
        // 検証するAPIパス
        final String API_PATH7 = "/items";

        // APIを実行
        resultActions = this.mockMvc.perform(MockMvcRequestBuilders.get(API_PATH7));
        // レスポンスを出力して、ステータスコードを検証
        resultActions
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk());
        // JSONの件数が3件であることを検証
        // リクエストボディを文字列(JSON)をJavaオブジェクトに変換して、Listのサイズをassert
        contentAsString = resultActions.andReturn().getResponse()
            .getContentAsString(StandardCharsets.UTF_8);
        itemResponseList = objectMapper.readValue(contentAsString,
            new TypeReference<List<ItemResponse>>() {
            });
        assertThat(itemResponseList, hasSize(2));
    }
}
