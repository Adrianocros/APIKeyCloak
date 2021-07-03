package com.apisegura.APISpringSecurity;


import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
public class Controller {


    private final List<Message>messages;

    public Controller(List<Message> messages) {
        this.messages = new ArrayList<>();

    }
    @RolesAllowed("user")
    @GetMapping
    public List<Message> findAll(@RequestParam(required = false) String message){
        if(message != null) {
            return messages.stream()
                    .filter(msg -> msg.getMessage().contains(message))
                    .collect(Collectors.toList());
        }
        return  messages;
    }

    @GetMapping("/{id}")
    public Message findById(@PathVariable("id") Integer id){
        return this.messages.stream()
                .filter(msg -> msg.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @PostMapping
    public Integer add(@RequestBody final Message message){
        if(message.getId() == null){
            message.setId(messages.size() + 1);
        }
        messages.add(message);
        return message.getId();
    }

    @PutMapping
    public void update(@RequestBody final Message message) {
    messages.stream().filter(msg -> msg.getId().equals(message.getId()))
    .forEach(msg-> msg.setMessage(message.getMessage()));
    }

    @DeleteMapping("/{id}")
    public void delet(@PathVariable("id") Integer id){
        messages.removeIf(msg -> msg.getId().equals(id));
    }

}
