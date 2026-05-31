@Repository
public interface FavoriteRepository
        extends JpaRepository<Favorite, Long> {

    List<Favorite> findByUser(User user);

    Optional<Favorite>
        findByUserAndCharacter(
                User user,
                Character character);
}