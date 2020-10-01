package com.crud.tasks.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TrelloBadges {

    private int votes;
    private TrelloAttachmentsByType attachmentsByType;

}
