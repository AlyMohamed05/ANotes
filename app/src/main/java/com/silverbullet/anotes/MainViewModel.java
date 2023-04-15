package com.silverbullet.anotes;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.silverbullet.anotes.core.model.Note;
import com.silverbullet.anotes.core.repository.NotesRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class MainViewModel extends ViewModel {

    private final NotesRepository repository;

    private final CompositeDisposable disposable;
    private final MutableLiveData<List<Note>> notesLiveData;

    @Inject
    public MainViewModel(NotesRepository repository) {
        this.repository = repository;

        disposable = new CompositeDisposable();
        notesLiveData = new MutableLiveData<>();

        disposable.add(
                repository
                        .observeNotes()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                notesLiveData::setValue,
                                throwable -> Log.w("MainViewModel", "Error in observing notes", throwable)
                        )
        );
    }

    public LiveData<List<Note>> notesLiveData() {
        return notesLiveData;
    }

    public void saveNote(Note note) {
        disposable.add(
                repository
                        .create(note)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> {
                                },
                                throwable -> Log.w("MainViewModel", "Failed to insert note", throwable)
                        )
        );
    }

    public void updateNote(Note note) {
        disposable.add(
                repository
                        .updateNote(note)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> {
                                },
                                throwable -> Log.w("MainViewModel", "Failed to insert note", throwable)
                        )
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
