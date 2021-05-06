package de.mk.learning.rxjava.rxjavademo.data.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Customer {

    private long id;

    /**
     * Do not fill field
     */
    private List<Notes> notes;

    private String firstName;

    private String lastName;

    private String codebar;

    private String description;

}
