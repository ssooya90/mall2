package com.convave.mall2api.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "tbl_todo")
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Todo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long tno;
	private String title;
	private String writer;
	private boolean complete;
	private LocalDate dueDate;

	public void update(String title, String writer, LocalDate dueDate, boolean complete){
		this.title = title;
		this.writer = writer;
		this.dueDate = dueDate;
		this.complete = complete;
	}
}
