package com.progetto.animeuniverse.data.source.genres;

import static com.progetto.animeuniverse.util.Constants.ENCRYPTED_DATA_FILE_NAME;
import static com.progetto.animeuniverse.util.Constants.ENCRYPTED_SHARED_PREFERENCES_FILE_NAME;
import static com.progetto.animeuniverse.util.Constants.LAST_UPDATE;
import static com.progetto.animeuniverse.util.Constants.SHARED_PREFERENCES_FILE_NAME;

import com.progetto.animeuniverse.database.AnimeRoomDatabase;
import com.progetto.animeuniverse.database.GenreDao;
import com.progetto.animeuniverse.model.Anime;
import com.progetto.animeuniverse.model.Genre;
import com.progetto.animeuniverse.model.GenresApiResponse;
import com.progetto.animeuniverse.util.DataEncryptionUtil;
import com.progetto.animeuniverse.util.SharedPreferencesUtil;

import java.util.List;

public class GenresLocalDataSource extends BaseGenresLocalDataSource{
    private final GenreDao genreDao;
    private final SharedPreferencesUtil sharedPreferencesUtil;
    private final DataEncryptionUtil dataEncryptionUtil;

    public GenresLocalDataSource(AnimeRoomDatabase animeRoomDatabase, SharedPreferencesUtil sharedPreferencesUtil, DataEncryptionUtil dataEncryptionUtil) {
        this.genreDao = animeRoomDatabase.genreDao();
        this.sharedPreferencesUtil = sharedPreferencesUtil;
        this.dataEncryptionUtil = dataEncryptionUtil;
    }


    @Override
    public void getGenre() {
        AnimeRoomDatabase.databaseWriteExecutor.execute(()->{
            GenresApiResponse genresApiResponse = new GenresApiResponse();
            genresApiResponse.setGernesList(genreDao.getAllGenres());
            genresCallback.onSuccessFromLocal(genresApiResponse);
        });
    }

    @Override
    public void updateGenre(Genre genre) {
        if(genre == null){
            List<Genre> allGenres = genreDao.getAllGenres();
            for(Genre g : allGenres){
                g.setSynchronized(false);
            }
        }
    }

    @Override
    public void insertGenres(GenresApiResponse genresApiResponse) {
        AnimeRoomDatabase.databaseWriteExecutor.execute(()->{
            List<Genre> allGenres = genreDao.getAllGenres();
            List<Genre> genresList = genresApiResponse.getGernesList();
            if(genresList != null){
                for(Genre genre : allGenres){
                    if(genresList.contains(genre)){
                        genresList.set(genresList.indexOf(genre), genre);
                    }
                }
                List<Long> insertedGenresIds = genreDao.insertGenreList(genresList);
                for (int i=0; i<genresList.size(); i++){
                    genresList.get(i).setId(Math.toIntExact(insertedGenresIds.get(i)));
                }
                sharedPreferencesUtil.writeStringData(SHARED_PREFERENCES_FILE_NAME, LAST_UPDATE, String.valueOf(System.currentTimeMillis()));
                genresCallback.onSuccessFromLocal(genresApiResponse);
            }
        });
    }

    @Override
    public void insertGenres(List<Genre> genresList) {
        AnimeRoomDatabase.databaseWriteExecutor.execute(()->{
            if(genresList != null){
                List<Genre> allGenres = genreDao.getAllGenres();
                for(Genre genre : allGenres){
                    if(genresList.contains(genre)){
                        genre.setSynchronized(true);
                        genresList.set(genresList.indexOf(genre), genre);
                    }
                }
                List<Long> insertedGenresIds = genreDao.insertGenreList(genresList);
                for(int i=0; i<genresList.size(); i++){
                    genresList.get(i).setId(Math.toIntExact(insertedGenresIds.get(i)));
                }
            }   GenresApiResponse genresApiResponse = new GenresApiResponse();
            genresApiResponse.setGernesList(genresList);
        });
    }

    @Override
    public void deleteAll() {
        AnimeRoomDatabase.databaseWriteExecutor.execute(()->{
            int genresCounter = genreDao.getAllGenres().size();
            int genresDeleteGenre = genreDao.deleteAll();
            if(genresCounter == genresDeleteGenre){
                sharedPreferencesUtil.deleteAll(SHARED_PREFERENCES_FILE_NAME);
                dataEncryptionUtil.deleteAll(ENCRYPTED_SHARED_PREFERENCES_FILE_NAME, ENCRYPTED_DATA_FILE_NAME);
                genresCallback.onSuccessDeletion();
            }
        });

    }
}
