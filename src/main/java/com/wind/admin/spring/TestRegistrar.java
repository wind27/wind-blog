package com.wind.admin.spring;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * TestRegistrar
 *
 * @author qianchun
 * @date 2018/12/19
 **/
public class TestRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {

    }



    public static void main(String[] args) {
        String toConvert = "";
        String prefix = "";
        String UTF8 = "UTF-8";
        String privateKeyIndex = "";

        try {
            String encrypted = toConvert.substring(prefix.length());
            byte[] encryptedBytes = Base64.getDecoder().decode(encrypted.getBytes(UTF8));

            String privateKeyVal = "MIICcwIBADANBgkqhkiG9w0BAQEFAASCAl0wggJZAgEAAoGBAIjFrOMjuQJlKdZfyo8y6tarOFtUgJsydwAvXcJ0Y0taknQ+vzYI7q+rCBgMJDSi2DMq9ttccnAABgO4FJId8y1VWrR/BuMaeyd2nyssAlEUCSzcbzreJDGdvofvfJc36vngXJS3kj061a1IBc9A1AlMJ+8lCdA+eXrA1B88SDLlAgMBAAECfzNJ7Ys/2iELzqSVR2Brt6TIkFmvVorcYcOv/3jsLFAVptXwscORlfBKMr7RzsHuTyTg97FK+Z8GYzsBzLT+hjHsoxmb55LAf0FOivjH2GeHOPyu6ipeGVhm0WPqO9lK6yz5mE57jlsMOegC9UmqEk0pR/C27MKVUZjfyzbvdIECQQDQsPRYevNbQo/GFjjB22XI+wPSRDRVgUR15yelHkKPR0Ac/kkq4yMwMpjh8gU1gwcqAYoyN+MsbHj6EM//6qpvAkEAp8cE094d/wiS4RLe4qPCTAzJSm4YcpkGfL1Dq1716+0ktqWLV7eIbmszw8WoYlvELe4foINTtyyGmGMSn+mx6wJAYjRjR5/UFQXlklmyq8cKxuEYk0H1PqduB+2XAmSS/cmRqhMfa0cnr+6fcvPo752RKXtABRG3nZgN9d8jglJzkQI/QtkJE2FskjDYVNUv+R1GTQbLLNyw4PgXnZvLWnx7f4T9G8jZvBlkQpp0BYy7inUFtUcrUskAkfsrpYvT3+M9AkEAgJOVTa22AYyB4Kv9kvtw6IyCMy4qyvYsyP96wH6g9bjSRi/IO5sFDbqvl7XqKnpi+Iu4kJJfFiEynMWWCR0ehQ==";
            String publicKeyVal = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCIxazjI7kCZSnWX8qPMurWqzhbVICbMncAL13CdGNLWpJ0Pr82CO6vqwgYDCQ0otgzKvbbXHJwAAYDuBSSHfMtVVq0fwbjGnsndp8rLAJRFAks3G863iQxnb6H73yXN+r54FyUt5I9OtWtSAXPQNQJTCfvJQnQPnl6wNQfPEgy5QIDAQAB";


//            String decrypt = new String(decryptedBytes, UTF8);
//
//            System.out.println("decrypt success! encrypt="+encrypted+", privateKeyIndex="+privateKeyIndex );
//            return decrypt;
//            return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }
}
