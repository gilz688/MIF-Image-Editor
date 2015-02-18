package com.github.gilz688.mifeditor.tests;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.github.gilz688.mifeditor.MIEInteractorImpl;
import com.github.gilz688.mifeditor.MIEPresenterImpl;
import com.github.gilz688.mifeditor.mif.MIFImage;
import com.github.gilz688.mifeditor.proto.MIEInteractor;
import com.github.gilz688.mifeditor.proto.MIEPresenter;
import com.github.gilz688.mifeditor.proto.MIEView;

@RunWith(MockitoJUnitRunner.class)
public class MIEPresenterImplTest {

	@Before
	public void setUp() throws Exception {

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testOnAbout(){
		MIEView mockView = Mockito.mock(MIEView.class);
		MIEInteractor mockInteractor = Mockito.mock(MIEInteractorImpl.class);
		MIEPresenter presenter = new MIEPresenterImpl(mockView, mockInteractor);
		presenter.onAbout();
	    Mockito.verify(mockView,Mockito.times(1)).showAboutDialog();
	}

	@Test
	public void testOnNew(){
		MIEView mockView = Mockito.mock(MIEView.class);
		MIEInteractor mockInteractor = Mockito.mock(MIEInteractorImpl.class);
		MIEPresenter presenter = new MIEPresenterImpl(mockView, mockInteractor);
		presenter.onNew();
	}

	@Test
	public void testOnOpen() throws FileNotFoundException, IOException{
		MIEView mockView = Mockito.mock(MIEView.class);
		MIEInteractor mockInteractor = Mockito.mock(MIEInteractorImpl.class);
		MIEPresenter presenter = new MIEPresenterImpl(mockView, mockInteractor);
		
		// create mock File and MIFImage
		File mockFile = Mockito.mock(File.class);
		MIFImage mockImage = Mockito.mock(MIFImage.class);
		Mockito.when(mockInteractor.openMIFImage(mockFile)).thenReturn(mockImage);
		Mockito.when(mockImage.getpWidth()).thenReturn(500);
		Mockito.when(mockImage.size()).thenReturn(1000);
		
		// return mock File in Open Dialog
		Mockito.when(mockView.showOpenDialog()).thenReturn(mockFile);
		presenter.onOpen();
		
	    Mockito.verify(mockView,Mockito.times(1)).showOpenDialog();
	    Mockito.verify(mockView,Mockito.times(1)).showImage(Mockito.anyObject());
	}
	
	@Test
	public void testOnOpenCancelled(){
		MIEView mockView = Mockito.mock(MIEView.class);
		MIEInteractor mockInteractor = Mockito.mock(MIEInteractorImpl.class);
		MIEPresenter presenter = new MIEPresenterImpl(mockView, mockInteractor);
		
		// cancel Open Dialog
		Mockito.when(mockView.showOpenDialog()).thenReturn(null);
		presenter.onOpen();
		
	    Mockito.verify(mockView,Mockito.times(1)).showOpenDialog();
	    Mockito.verify(mockView,Mockito.never()).showImage(Mockito.anyObject());
	}
	
	@Test
	public void testOnSave(){
		MIEView mockView = Mockito.mock(MIEView.class);
		MIEInteractor mockInteractor = Mockito.mock(MIEInteractorImpl.class);
		MIEPresenter presenter = new MIEPresenterImpl(mockView, mockInteractor);
		
		// select dummy file in save dialog
		File dummyFile = new File("dummy.mif");
		Mockito.when(mockView.showSaveDialog()).thenReturn(dummyFile);
		
		presenter.onSave();
	    Mockito.verify(mockView,Mockito.times(1)).showSaveDialog();
	}
	
	@Test
	public void testOnSaveCancelled(){
		MIEView mockView = Mockito.mock(MIEView.class);
		MIEInteractor mockInteractor = Mockito.mock(MIEInteractorImpl.class);
		MIEPresenter presenter = new MIEPresenterImpl(mockView, mockInteractor);
		
		// cancel save dialog
		Mockito.when(mockView.showSaveDialog()).thenReturn(null);
		
		presenter.onSave();
	    Mockito.verify(mockView,Mockito.times(1)).showSaveDialog();
	}

	@Test
	public void testOnSaveWithCurrentlyOpenedFile() throws FileNotFoundException, IOException{
		MIEView mockView = Mockito.mock(MIEView.class);
		MIEInteractor mockInteractor = Mockito.mock(MIEInteractorImpl.class);
		MIEPresenter presenter = new MIEPresenterImpl(mockView, mockInteractor);
		
		// return mock File in Open Dialog
		File mockFile = Mockito.mock(File.class);
		Mockito.when(mockFile.getName()).thenReturn("dummy.mif");
		Mockito.when(mockView.showOpenDialog()).thenReturn(mockFile);
		
		// return mock MIFImage in MIEInteractor
		MIFImage mockImage = Mockito.mock(MIFImage.class);
		Mockito.when(mockInteractor.openMIFImage(mockFile)).thenReturn(mockImage);
		Mockito.when(mockImage.getpWidth()).thenReturn(500);
		Mockito.when(mockImage.size()).thenReturn(1000);
		
		presenter.onOpen();
		presenter.onSave();
	    Mockito.verify(mockView,Mockito.never()).showSaveDialog();
	}
	
	@Test
	public void testOnSaveAs(){
		MIEView mockView = Mockito.mock(MIEView.class);
		MIEInteractor mockInteractor = Mockito.mock(MIEInteractorImpl.class);
		MIEPresenter presenter = new MIEPresenterImpl(mockView, mockInteractor);
		presenter.onSaveAs();
	    Mockito.verify(mockView,Mockito.times(1)).showSaveDialog();
	}

	@Test
	public void testOnQuit(){
		
	}

	@Test
	public void testOnMousePressed(){
		MIEView mockView = Mockito.mock(MIEView.class);
		MIEInteractor mockInteractor = Mockito.mock(MIEInteractorImpl.class);
		MIEPresenter presenter = new MIEPresenterImpl(mockView, mockInteractor);
		presenter.onMousePressed(5, 5);
		
		// verify if image is refreshed in MIEView
		Mockito.verify(mockView,Mockito.times(1)).showImage(Mockito.anyObject());
	}

	@Test
	public void testOnZoom(){
		MIEView mockView = Mockito.mock(MIEView.class);
		MIEInteractor mockInteractor = Mockito.mock(MIEInteractorImpl.class);
		MIEPresenter presenter = new MIEPresenterImpl(mockView, mockInteractor);
		
		int zoom = 150;
		double scaleFactor = zoom/100.0f;
		
		presenter.onZoom(zoom);
		Mockito.verify(mockView,Mockito.times(1)).scaleImage(scaleFactor);
	}
}
