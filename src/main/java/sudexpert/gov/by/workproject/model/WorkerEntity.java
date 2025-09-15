    package sudexpert.gov.by.workproject.model;


    import io.swagger.v3.oas.annotations.media.Schema;
    import jakarta.persistence.*;
    import lombok.*;
    import lombok.experimental.FieldDefaults;
    import org.hibernate.annotations.ColumnDefault;

    import java.time.LocalDate;
    import java.util.Set;

    @Getter
    @Setter
    @Entity
    @NoArgsConstructor
    @AllArgsConstructor
    @Table(name = "workers")
    @Schema(description = "Worker Entity Info.")
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public class WorkerEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Schema(description = "id", example = "2")
        Long id;

        @Schema(description = "Имя", example = "Иван")
        @Column(name = "name")
        String name;

        @Schema(description = "Фамилия", example = "Иванов")
        @Column(name = "surname")
        String surname;

        @Schema(description = "Отчество", example = "Иванович")
        @Column(name = "patronymic_name")
        String patronymicName;

        @Schema(description = "Должность", example = "Эксперт")
        @Column(name = "job_title")
        String jobTitle;

        //@ElementCollection(targetClass = Train.class)
        @OneToMany(mappedBy = "worker", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
        @Schema(description = "Стажировки")
        Set<Train> trains;

        @OneToMany(mappedBy = "worker", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
        @Schema(description = "Категория")
        Set<Category> categories;

        @OneToMany(mappedBy = "worker", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
        @Schema(description = "ЭКК")
        Set<ECC> eccs;

        @OneToMany(mappedBy = "worker", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
        @Schema(description = "Повышение квалификации")
        Set<Qualification> qualifications;

        @OneToMany(mappedBy = "worker", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
        @Schema(description = "Отпуск")
        Set<Vacation> vacations;

        @Schema(description = "Находится ли в отпуске", example = "true")
        @Column(name = "is_vacated")
        @ColumnDefault("false")
        Boolean isVacated;

        @Schema(description = "Будет ли в отпуске в ближайшие 3 месяца", example = "true")
        @Column(name = "is_vacated_in_3_months")
        Boolean isVacatedIn3Months;

        @Schema(description = "Будет ли категория в следующем году", example = "true")
        @Column(name = "is_category_next_year")
        Boolean isCategoryNextYear;

        @Schema(description = "Стажировка в следующем месяце", example = "true")
        @Column(name = "is_train_next_month")
        Boolean isTrainNextMonth;

        @Schema(description = "Будет ли ЭКК в следующием месяце", example = "true")
        @Column(name = "is_ecc_next_month")
        Boolean isEccNextMonth;

        @Schema(description = "Будет ли повышение квалификации в следующем месяце", example = "true")
        @Column(name = "is_qualification_next_month")
        Boolean isQualificationNextMonth;

        @Schema(description = "Будет ли категория в следующих 3 месяцев", example = "true")
        @Column(name = "is_category_next_3_month")
        Boolean isCategoryNext3Month;

        @Schema(description = "Кратен ли день рождения 5", example = "true")
        @Column(name = "is_bday5")
        Boolean isBday5;

        @Schema(description = "День рождения", example = "2024-09-18")
        @Column(name = "b_day")
        LocalDate birthDay;

        @OneToMany(mappedBy = "worker", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
        @Schema(description = "Регалии")
        Set<Achievement> achievements;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof WorkerEntity that)) return false;
            return id != null && id.equals(that.id);
        }

        @Override
        public int hashCode() {
            return id != null ? id.hashCode() : System.identityHashCode(this);
        }

        @PrePersist
        public void prePersist() {
            if (isVacated == null) isVacated = false;
            if (isVacatedIn3Months == null) isVacatedIn3Months = false;
            if (isCategoryNextYear == null) isCategoryNextYear = false;
            if (isTrainNextMonth == null) isTrainNextMonth = false;
            if (isEccNextMonth == null) isEccNextMonth = false;
            if (isQualificationNextMonth == null) isQualificationNextMonth = false;
            if (isCategoryNext3Month == null) isCategoryNext3Month = false;
            if (isBday5 == null) isBday5 = false;
        }
    }
