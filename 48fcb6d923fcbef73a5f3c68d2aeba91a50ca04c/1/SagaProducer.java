package io.choerodon.asgard.saga.demo.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.choerodon.asgard.saga.SagaClient;
import io.choerodon.asgard.saga.demo.producer.mapper.AsgardUserMapper;
import io.choerodon.asgard.saga.dto.StartInstanceDTO;
import io.choerodon.core.exception.CommonException;
import io.choerodon.core.saga.Saga;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Saga(code = "asgard-create-user", description = "创建项目", inputSchema = "{}")
public class SagaProducer {

    private SagaClient sagaClient;

    private AsgardUserMapper asgardUserMapper;

    private ObjectMapper objectMapper = new ObjectMapper();

    public SagaProducer(SagaClient sagaClient, AsgardUserMapper asgardUserMapper) {
        this.sagaClient = sagaClient;
        this.asgardUserMapper = asgardUserMapper;
    }

    @PostMapping("/v1/users")
    @Transactional
    public AsgardUser createUser(@Valid @RequestBody AsgardUser user) {
        if (asgardUserMapper.insertSelective(user) != 1) {
            throw new CommonException("error.AsgardUser.create");
        }
        try {
            String input = objectMapper.writeValueAsString(asgardUserMapper.selectByPrimaryKey(user.getId()));
            //userId: DetailsHelper.getUserDetails().getUserId()，从userDetails获取
            sagaClient.startSaga("asgard-create-user", new StartInstanceDTO(input, 10L, "", ""));
            return user;
        } catch (JsonProcessingException e) {
            throw new CommonException("error.SagaProducer.createUser");
        }
    }

}
