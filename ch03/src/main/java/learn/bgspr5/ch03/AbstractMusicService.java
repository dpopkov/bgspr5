package learn.bgspr5.ch03;

import learn.bgspr5.ch03.model.Artist;
import learn.bgspr5.ch03.model.Song;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class AbstractMusicService implements MusicService, Resettable {

    private final Map<String, Artist> bands = new HashMap<>();

    /**
     * Transforms Artist's name.
     * Classes that extend AbstractMusicService can override this method,
     * possibly by delegating to a {@link Normalizer} interface
     */
    protected String transformArtist(String input) {
        return input;
    }

    /**
     * Transforms Song's name.
     * Classes that extend AbstractMusicService can override this method,
     * possibly by delegating to a {@link Normalizer} interface
     */
    protected String transformSong(String input) {
        return input;
    }

    @Override
    public void reset() {
        bands.clear();
    }

    private Artist getArtist(String artistName) {
        String normalizedName = transformArtist(artistName);
        return bands.computeIfAbsent(normalizedName, s -> new Artist(normalizedName));
    }

    @Override
    public Song getSong(String artistName, String songName) {
        Artist artist = getArtist(artistName);
        String normalizedTitle = transformSong(songName);
        return artist.getSongs()
                .computeIfAbsent(normalizedTitle, Song::new);
    }

    @Override
    public List<Song> getSongsForArtist(String artistName) {
        List<Song> songs = new ArrayList<>(getArtist(artistName).getSongs().values());
        songs.sort(Song::compareTo);
        return songs;
    }

    @Override
    public List<String> getMatchingSongNamesForArtist(String artistName, String songPrefix) {
        String normalizedPrefix = transformSong(songPrefix).toLowerCase();
        final Set<String> songNames = getArtist(artistName).getSongs().keySet();
        return songNames.stream()
                .map(this::transformSong)
                .filter(songName -> songName.toLowerCase().startsWith(normalizedPrefix))
                .sorted(Comparator.comparing(Function.identity()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getMatchingArtistNames(String prefix) {
        String normalizedPrefix = transformSong(prefix).toLowerCase();
        return bands.keySet()
                .stream()
                .filter(name -> name.toLowerCase().startsWith(normalizedPrefix))
                .sorted(Comparator.comparing(Function.identity()))
                .collect(Collectors.toList());
    }

    @Override
    public Song voteForSong(String artist, String name) {
        Song song = getSong(artist, name);
        song.setVotes(song.getVotes() + 1);
        return song;
    }
}
