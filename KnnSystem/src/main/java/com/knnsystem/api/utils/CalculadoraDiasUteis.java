package com.knnsystem.api.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

import static java.time.DayOfWeek.*;


public class CalculadoraDiasUteis {

        private static final Set<DayOfWeek> DIAS_UTEIS = Set.of(
                MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY
        );

        public static Long calculaDiasUteisEntre(LocalDate dataInicio, LocalDate dataFim){
            // TODO: descontar feriados na semana
            return dataInicio.datesUntil(dataFim)
                    .filter(dt -> DIAS_UTEIS.contains(dt.getDayOfWeek()))
                    .count();
        }



}
