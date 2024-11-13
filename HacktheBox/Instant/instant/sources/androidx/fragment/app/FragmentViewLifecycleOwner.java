package androidx.fragment.app;

import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Bundle;
import androidx.lifecycle.HasDefaultViewModelProviderFactory;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.savedstate.SavedStateRegistry;
import androidx.savedstate.SavedStateRegistryController;
import androidx.savedstate.SavedStateRegistryOwner;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class FragmentViewLifecycleOwner implements HasDefaultViewModelProviderFactory, SavedStateRegistryOwner, ViewModelStoreOwner {
    private ViewModelProvider.Factory mDefaultFactory;
    private final Fragment mFragment;
    private LifecycleRegistry mLifecycleRegistry = null;
    private SavedStateRegistryController mSavedStateRegistryController = null;
    private final ViewModelStore mViewModelStore;

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean isInitialized() {
        return this.mLifecycleRegistry != null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public FragmentViewLifecycleOwner(Fragment fragment, ViewModelStore viewModelStore) {
        this.mFragment = fragment;
        this.mViewModelStore = viewModelStore;
    }

    @Override // androidx.lifecycle.ViewModelStoreOwner
    public ViewModelStore getViewModelStore() {
        initialize();
        return this.mViewModelStore;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void initialize() {
        if (this.mLifecycleRegistry == null) {
            this.mLifecycleRegistry = new LifecycleRegistry(this);
            this.mSavedStateRegistryController = SavedStateRegistryController.create(this);
        }
    }

    @Override // androidx.lifecycle.LifecycleOwner
    public Lifecycle getLifecycle() {
        initialize();
        return this.mLifecycleRegistry;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setCurrentState(Lifecycle.State state) {
        this.mLifecycleRegistry.setCurrentState(state);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void handleLifecycleEvent(Lifecycle.Event event) {
        this.mLifecycleRegistry.handleLifecycleEvent(event);
    }

    @Override // androidx.lifecycle.HasDefaultViewModelProviderFactory
    public ViewModelProvider.Factory getDefaultViewModelProviderFactory() {
        Application application;
        ViewModelProvider.Factory defaultViewModelProviderFactory = this.mFragment.getDefaultViewModelProviderFactory();
        if (!defaultViewModelProviderFactory.equals(this.mFragment.mDefaultFactory)) {
            this.mDefaultFactory = defaultViewModelProviderFactory;
            return defaultViewModelProviderFactory;
        }
        if (this.mDefaultFactory == null) {
            Context applicationContext = this.mFragment.requireContext().getApplicationContext();
            while (true) {
                if (!(applicationContext instanceof ContextWrapper)) {
                    application = null;
                    break;
                }
                if (applicationContext instanceof Application) {
                    application = (Application) applicationContext;
                    break;
                }
                applicationContext = ((ContextWrapper) applicationContext).getBaseContext();
            }
            this.mDefaultFactory = new SavedStateViewModelFactory(application, this, this.mFragment.getArguments());
        }
        return this.mDefaultFactory;
    }

    @Override // androidx.savedstate.SavedStateRegistryOwner
    public SavedStateRegistry getSavedStateRegistry() {
        initialize();
        return this.mSavedStateRegistryController.getSavedStateRegistry();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void performRestore(Bundle bundle) {
        this.mSavedStateRegistryController.performRestore(bundle);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void performSave(Bundle bundle) {
        this.mSavedStateRegistryController.performSave(bundle);
    }
}