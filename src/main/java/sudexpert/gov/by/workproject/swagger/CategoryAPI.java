package sudexpert.gov.by.workproject.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import sudexpert.gov.by.workproject.dto.CategoryDTO;
import sudexpert.gov.by.workproject.dto.error.AppError;
import sudexpert.gov.by.workproject.dto.request.create.CreateCategoryDTO;
import sudexpert.gov.by.workproject.dto.response.CategoryResponse;
import sudexpert.gov.by.workproject.dto.validation.OnCreate;

import java.util.List;

@Tag(name = "Book Controller", description = "Category API")
public interface CategoryAPI {
    @Operation(summary = "Create category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Category created successfully", content = @Content(schema = @Schema(implementation = CategoryDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content(schema = @Schema(implementation = AppError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = AppError.class)))
    })
    CategoryDTO createCategory(@RequestBody @Validated(OnCreate.class) CategoryDTO createCategoryDTO);

    @Operation(summary = "Get all categories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Books retrieved successfully", content = @Content(schema = @Schema(implementation = CategoryDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = AppError.class)))
    })
    List<CategoryDTO> getAllCategories();
}
