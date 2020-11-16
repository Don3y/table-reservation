package hu.elte.reservationtrackerrest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import hu.elte.reservationtrackerrest.entities.ResturantTable;

import hu.elte.reservationtrackerrest.entities.User;
import java.util.List;
import static org.assertj.core.api.Java6Assertions.assertThat;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class ReservationControllerRestTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String getTokenForUser(String username, String password) throws Exception {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        
        ResponseEntity<String> responseAuth = restTemplate.postForEntity("/api/auth", user, String.class);
        String jsonString = responseAuth.getBody();
        JSONObject json2 = new JSONObject(jsonString);
        return json2.getString("token");
    }
    
    private HttpEntity getRequestEntityForUser(String username, String password) throws Exception {
        String token = getTokenForUser(username, password);
        
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", "token=" + token);
//        headers.add("Authorization", "Bearer " + token);
        return new HttpEntity(null, headers);
    }
    
    @Test
    public void greetingShouldReturnDefaultMessage() throws Exception {
        System.out.println("Test 1");
        HttpEntity requestEntity = getRequestEntityForUser("user1", "user");
        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:" + port + "/hello",
                HttpMethod.GET,
                requestEntity,
                String.class
        );
        
        assertThat(response.getBody()).contains("world");
    }
    
       @Test
    public void shouldReturnAllTableForUser() throws Exception {
        System.out.println("Test 2");
        HttpEntity requestEntity = getRequestEntityForUser("user1", "user");
        ResponseEntity<List<ResturantTable>> response = restTemplate.exchange("http://localhost:" + port + "/tables", HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<ResturantTable>>() {});
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().size()).isEqualTo(5);
    }
    
   @Test
    public void shouldReturnAllTableForAdmin() throws Exception {
        System.out.println("Test 3");
        HttpEntity requestEntity = getRequestEntityForUser("admin", "admin");
        ResponseEntity<List<ResturantTable>> response = restTemplate.exchange(
                "http://localhost:" + port + "/tables", 
                HttpMethod.GET, 
                requestEntity, 
                new ParameterizedTypeReference<List<ResturantTable>>() {});
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().size()).isEqualTo(5);
    }
    
    @Test
    public void shouldReturnTheFirstTable() throws Exception {
        System.out.println("Test 4");
        HttpEntity requestEntity = getRequestEntityForUser("user1", "user");
        ResponseEntity<ResturantTable> response = restTemplate.exchange(
                "http://localhost:" + port + "/tables/1",
                HttpMethod.GET,
                requestEntity,
                ResturantTable.class
        );
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        ResturantTable issue = response.getBody();
        assertThat(issue.getId()==1);

    }
   @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void shouldSaveAPostedIssue() throws Exception {
        System.out.println("Test 6");
        HttpEntity requestEntity = getRequestEntityForUser("admin", "admin");
        ResponseEntity<List<ResturantTable>> responseList = restTemplate.exchange("http://localhost:" + port + "/tables", HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<ResturantTable>>() {});
        assertThat(responseList.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseList.getBody().size()).isEqualTo(5);
        
        ResturantTable table = new ResturantTable();
        table.setId(6);      
        table.setStatus(ResturantTable.Status.FREE);
        
        String token = getTokenForUser("admin", "admin");
        
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", "token=" + token);
        headers.add("Content-Type", "application/json");
        
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(table);
        HttpEntity<String> requestEntityWithBody = new HttpEntity<String>(json, headers);
   
//        System.out.println(json);
        ResponseEntity<ResturantTable> responsePost = restTemplate.exchange(
                "http://localhost:" + port + "/tables",
                HttpMethod.POST,
                requestEntityWithBody,
                ResturantTable.class
        );
//        System.out.println(responsePost.getBody());
        assertThat(responsePost.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responsePost.getBody().getId()).isNotNull();
        assertThat(responsePost.getBody().getId()).isEqualTo(6);    
        assertThat(responsePost.getBody().getStatus()).isEqualTo(ResturantTable.Status.FREE);

        
        ResponseEntity<List<ResturantTable>> responseList2 = restTemplate.exchange("http://localhost:" + port + "/tables", HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<ResturantTable>>() {});
        assertThat(responseList2.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseList2.getBody().size()).isEqualTo(5);
    }
    
  
    
}
