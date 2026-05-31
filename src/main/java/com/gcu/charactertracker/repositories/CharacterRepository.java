@Repository
public interface CharacterRepository
    extends JpaRepository<Character, Long> {

    List<Character>
    findByCharacterNameContainingIgnoreCase(String keyword);

}