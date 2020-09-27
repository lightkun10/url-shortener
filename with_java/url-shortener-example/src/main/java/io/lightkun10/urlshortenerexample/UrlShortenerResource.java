package io.lightkun10.urlshortenerexample;

import com.google.common.hash.Hashing;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;

@RequestMapping("/rest/url")
@RestController
public class UrlShortenerResource {

    @Autowired
    StringRedisTemplate redisTemplate;

    @GetMapping("/{id}")
    public String getUrl(@PathVariable String id) {

        /* Retrieve the value */
        String url = redisTemplate.opsForValue().get(id);
        System.out.println("URL Retrieved: " + url);

        // If id or url is error
        if (url == null) {
            throw new RuntimeException("There's not shorter URL for: " + id);
        }
        return url; // Full, raw url
    }

    /* Pass the URL */
    @PostMapping
    public String create(@RequestBody String url) {

        /* Validating passed URL */
        UrlValidator urlValidator = new UrlValidator(new String[]{"http", "https"});

        /* If URL is valid... */
        if(urlValidator.isValid(url)) {
            // Hashing
            // https://en.wikipedia.org/wiki/MurmurHash
            String id = Hashing.murmur3_32().hashString(url, StandardCharsets.UTF_8)
                        .toString();

            System.out.println("URL Id generated: " + id);

            if (redisTemplate.opsForValue().get(id) != null) {
                /* Store the hash id and url into a redis cache */
                redisTemplate.opsForValue().set(id, url); // Similar to populating an HashMap
                return id;
            }

//            /* Store the hash id and url into a redis cache */
//            redisTemplate.opsForValue().set(id, url); // Similar to populating an HashMap
//            return id;
        }
        /* If URL is not valid, throw an exception. */
        throw new RuntimeException("URL Invalid: " + url);
    }
}
