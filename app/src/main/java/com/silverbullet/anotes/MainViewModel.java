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
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
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

        repository
                .observeNotes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Note>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull List<Note> notes) {
                        notesLiveData.setValue(notes);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.w("MainViewModel","Error in Notes Observable",e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public LiveData<List<Note>> notesLiveData() {
        return notesLiveData;
    }

    public void saveNote(Note note) {
         repository
                .create(note);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
