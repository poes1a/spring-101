package io.people23.academy.controllers.api;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.people23.academy.models.Course;
import io.people23.academy.repositories.CourseRepository;

@Controller
@RequestMapping("/api/courses")
public class CoursesController {

	private static Logger logger = LoggerFactory.getLogger(CoursesController.class);

	private CourseRepository repository;

	@Autowired
	private CoursesController(CourseRepository repository) {
		this.repository = repository;
	}

	// Return paginated list of courses
	@GetMapping
	public @ResponseBody Page<Course> home(Pageable pageable, String filter) {

		logger.info("Paginated Courses");

		if (filter != null && !filter.isEmpty()) {
			return this.repository.findByNameContainingIgnoreCase(filter, pageable);
		}

		return this.repository.findAll(pageable);

	}

	// Return all courses, for use in select lists
	@GetMapping("/all")
	public @ResponseBody List<Course> all() {

		logger.info("All Courses");
		return (List<Course>) this.repository.findAll();
	}

	@GetMapping("/{id}")
	public @ResponseBody Course findById(@PathVariable(value = "id") Integer id) {

		logger.info("Find by Id");

		return this.repository.findOne(id);

	}

	@PostMapping
	public @ResponseBody Course create(@Valid @RequestBody Course model) {

		logger.info("Create Course");

		this.repository.save(model);

		return model;

	}

	@PutMapping("/{id}")
	public @ResponseBody Course update(@PathVariable(value = "id") Integer id, @RequestBody Course course) {

		logger.info("Update Course");
		Course c = this.repository.findOne(id);

		c.setName(course.getName());

		this.repository.save(c);

		return c;

	}

	@DeleteMapping("/{id}")
	public @ResponseBody String delete(@PathVariable(value = "id") Integer id) {

		logger.info("Delete Course");

		this.repository.delete(id);

		return "{\"message\": \"ok\"}";

	}

}
