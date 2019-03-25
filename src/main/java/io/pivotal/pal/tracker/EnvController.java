package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class EnvController {
    @Value("${port:NOT SET}")
    String port;
    @Value("${memory.limit:NOT SET}")
    String memoryLimit;
    @Value("${cf.instance.index:NOT SET}")
    private String cfInstanceIndex;
    @Value("${cf.instance.addr:NOT SET}")
    private String cfInstanceAddress;

    public EnvController(){

    }

    public EnvController(String port, String memoryLimit, String index, String address) {
        this.port = port;
        this.memoryLimit = memoryLimit;
        this.cfInstanceIndex = index;
        this.cfInstanceAddress = address;
    }

    @GetMapping("/env")
    public Map<String, String> getEnv() {
        Map m = new HashMap<String,String>();
        m.put("PORT",this.port);
        m.put("MEMORY_LIMIT",this.memoryLimit);
        m.put("CF_INSTANCE_INDEX",this.cfInstanceIndex);
        m.put("CF_INSTANCE_ADDR",this.cfInstanceAddress);
        return m;
    }
}
