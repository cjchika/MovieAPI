package com.cjchika.movieApi.controllers;

import com.cjchika.movieApi.dto.MovieDto;
import com.cjchika.movieApi.dto.MoviePageResponse;
import com.cjchika.movieApi.exceptions.EmptyFileException;
import com.cjchika.movieApi.service.MovieService;
import com.cjchika.movieApi.utils.AppConstants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/movie")
public class MovieController {

    private MovieService movieService;

    public MovieController(MovieService movieService){
        this.movieService = movieService;
    }

    @PostMapping("/add-movie")
    public ResponseEntity<MovieDto> addMovieHandler(@RequestPart MultipartFile file,
                                                    @RequestPart String movieDto) throws IOException, EmptyFileException {
        if(file.isEmpty()){
            throw new EmptyFileException("File is empty, please attach file!");
        }
        MovieDto dto = convertToMovieDto(movieDto);
        return new ResponseEntity<>(movieService.addMovie(dto, file), HttpStatus.CREATED);
    }

    @GetMapping("/{movieId}")
    public ResponseEntity<MovieDto>  getMovieHandler(@PathVariable Integer movieId){
        return new ResponseEntity<>(movieService.getMovie(movieId), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<MovieDto>> getAllMoviesHandler(){
        return new ResponseEntity<>(movieService.getAllMovies(), HttpStatus.OK);
    }

    @PutMapping("/{movieId}")
    public ResponseEntity<MovieDto> updateMovieHandler(@PathVariable Integer movieId,
                                                       @RequestPart MultipartFile file,
                                                       @RequestPart String movieDto) throws IOException {
        if(file.isEmpty()) file = null;
        MovieDto movieDtoObj = convertToMovieDto(movieDto);

        return new ResponseEntity<>(movieService.updateMovie(movieId, movieDtoObj, file), HttpStatus.OK);
    }

    @DeleteMapping("/{movieId}")
    public ResponseEntity<String> deleteMovieHandle(@PathVariable Integer movieId) throws IOException {
        return new ResponseEntity<>(movieService.deleteMovie(movieId), HttpStatus.OK);
    }

    @GetMapping("/paginatedMovies")
    public ResponseEntity<MoviePageResponse> getPaginatedMoviesHandler(
            @RequestParam(defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize
    ){
        return new ResponseEntity<>(movieService.getALlMoviesWithPagination(pageNumber, pageSize), HttpStatus.OK);
    }

    @GetMapping("/sortedMovies")
    public ResponseEntity<MoviePageResponse> getPaginatedMoviesHandler(
            @RequestParam(defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(defaultValue = AppConstants.SORT_DIR, required = false) String dir
    ){
        return new ResponseEntity<>(movieService.getAllMoviesWithPaginationAndSorting(pageNumber, pageSize, sortBy, dir), HttpStatus.OK);
    }

    private MovieDto convertToMovieDto(String movieDtoObj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(movieDtoObj, MovieDto.class);
    }
}
