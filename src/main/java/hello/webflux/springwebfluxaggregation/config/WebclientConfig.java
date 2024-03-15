package hello.webflux.springwebfluxaggregation.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebclientConfig {

    @Bean
    public WebClient webclient() {

        var connectionProvider = ConnectionProvider.builder("BW-connection-provider")
            .pendingAcquireMaxCount(-1)
            .maxIdleTime(Duration.ofSeconds(4)) //4초에 특별한 의미는 없다. connection-time-out보다 적은값을 우선 사용한다.
            .lifo()
            .build();

        var httpClient = HttpClient.create(connectionProvider).option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .doOnConnected(connection -> {
                        connection.addHandlerLast(new ReadTimeoutHandler(2000, TimeUnit.MILLISECONDS));
                        connection.addHandlerLast(new WriteTimeoutHandler(2000, TimeUnit.MILLISECONDS));
                })
                .responseTimeout(Duration.ofSeconds(2));

        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .codecs(codec -> codec.defaultCodecs().maxInMemorySize(5 * 1024 * 1024))
                .build();
    }
}
