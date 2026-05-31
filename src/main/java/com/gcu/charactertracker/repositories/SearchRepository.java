@Query("""
    SELECT c FROM Character c
    WHERE (:keyword IS NULL OR LOWER(c.characterName) LIKE LOWER(CONCAT('%', :keyword, '%')))
      AND (:race IS NULL OR c.race = :race)
      AND (:characterClass IS NULL OR c.characterClass = :characterClass)
""")
List<Character> search(
    @Param("keyword") String keyword,
    @Param("race") String race,
    @Param("characterClass") String characterClass
);