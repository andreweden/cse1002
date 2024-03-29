/******************************************************************************
 *  Compilation:  javac MyAudio.java
 *  Dependencies: none
 *  
 *  Simple library for reading, writing, and manipulating .wav files.
 *
 *
 *  Limitations
 *  -----------
 *    - Assumes the audio is monaural, little endian, with sampling rate
 *      of 44,100
 *    - check when reading .wav files from a .jar file ?
 *
 ******************************************************************************/

import javax.sound.sampled.Clip;

import java.io.File;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.IOException;

import java.net.URL;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineEvent;

/**
 *  <i>Standard audio</i>. This class provides a basic capability for
 *  creating, reading, and saving audio. 
 *  <p>
 *  The audio format uses a sampling rate of 44,100 Hz, 16-bit, monaural.
 *
 *  <p>
 *  For additional documentation, see <a href="https://introcs.cs.princeton.edu/15inout">Section 1.5</a> of
 *  <i>Computer Science: An Interdisciplinary Approach</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public final class MyAudio {

    private MyAudio() {
        // cannot instantiate
    }
   
    /**
     *  The sample rate: 44,100 Hz for CD quality audio.
     */
    public static final int SAMPLE_RATE = 44100;

    private static final int BYTES_PER_SAMPLE = 2;       // 16-bit audio
    private static final int BITS_PER_SAMPLE = 16;       // 16-bit audio
    private static final double MAX_16_BIT = 32768;
    private static final int SAMPLE_BUFFER_SIZE = 4096;

    // the internal buffer is a fraction of the actual buffer size, this choice is arbitrary
    // it gets divided because we can't expect the buffered data to line up exactly with when
    // the sound card decides to push out its samples.
    // our internal buffer
    private static byte[] buffer = new byte[SAMPLE_BUFFER_SIZE * BYTES_PER_SAMPLE/3];
    private static int bufferSize = 0;    // number of samples currently in internal buffer

    private static final int MONO   = 1;
    private static final int STEREO = 2;
    private static final boolean LITTLE_ENDIAN = false;
    private static final boolean BIG_ENDIAN    = true;
    private static final boolean SIGNED        = true;
    private static final boolean UNSIGNED      = false;

    private static final AudioFormat AUDIO_FORMAT = new AudioFormat((float) SAMPLE_RATE, BITS_PER_SAMPLE, MONO, SIGNED, LITTLE_ENDIAN);
    // 44,100 Hz, 16-bit audio, mono, signed pulse-code modulaton (PCM), little endian
    private static DataLine.Info LINE_INFO = new DataLine.Info (SourceDataLine.class, AUDIO_FORMAT);
    private static boolean LINE_SUPPORTED = AudioSystem.isLineSupported (LINE_INFO);

    private static SourceDataLine line;   // to play the sound


    // static initializer
    static {
        if (LINE_SUPPORTED) init();
        // no sound gets made before this call
        if (line!=null) line.start();
    }

    // open up an audio stream
    private static void init() {
       try {
            line = (SourceDataLine) AudioSystem.getLine(LINE_INFO);
            line.open(AUDIO_FORMAT, SAMPLE_BUFFER_SIZE * BYTES_PER_SAMPLE);
        } catch (final LineUnavailableException e) {
            System.out.println(e.getMessage());
        }
    }

    // get an AudioInputStream object from a file
    private static AudioInputStream getAudioInputStreamFromFile (final String filename) {
        try {
            // first try to read file from local file system
            final File file = new File(filename);
            if (file.exists()) {
                return AudioSystem.getAudioInputStream(file);
            }

            // resource relative to .class file
            final InputStream is1 = MyAudio.class.getResourceAsStream(filename);
            if (is1 != null) {
                return AudioSystem.getAudioInputStream(is1);
            }

            // resource relative to classloader root
            InputStream is2 = MyAudio.class.getClassLoader().getResourceAsStream(filename);
            if (is2 != null) {
                return AudioSystem.getAudioInputStream(is2);
            } else {
                throw new IllegalArgumentException("could not read '" + filename + "'");
            }
        } catch (final IOException e) {
            throw new IllegalArgumentException("could not read '" + filename + "'", e);
        } catch (final UnsupportedAudioFileException e) {
            throw new IllegalArgumentException("file of unsupported audio format: '" + filename + "'", e);
        }
    }



    /**
     * Reads audio samples from a file (in .wav or .au format) and returns
     * them as a double array with values between -1.0 and +1.0.
     * The audio file must be 16-bit with a sampling rate of 44,100.
     * It can be mono or stereo.
     *
     * @param  filename the name of the audio file
     * @return the array of samples
     */
    public static double[] read (final String filename) {
       if (filename == null) {
          throw new IllegalArgumentException("filename is null");
       }

       // make sure that AudioFormat is 16-bit, 44,100 Hz, little endian
       final AudioInputStream ais = getAudioInputStreamFromFile(filename);
       assert ais!=null;

       try {
          return read (ais);
       } catch (final IOException ioe) {
          throw new IllegalArgumentException(String.format ("could not read '%s'", filename));
       }
    }

   public static double[] read (final URL url) {
      if (url == null) {
         throw new IllegalArgumentException("url is null");
      }

      AudioInputStream ais = null;
      try {
         // make sure that AudioFormat is 16-bit, 44,100 Hz, little endian
         ais = AudioSystem.getAudioInputStream(url);
      } catch (final IOException ioe) {
         throw new IllegalArgumentException(String.format ("could not read url '%s'", url), ioe);
      } catch (final UnsupportedAudioFileException ue) {
         throw new IllegalArgumentException(String.format ("URL of unsupported audio format '%s'", url), ue);
      }
      assert ais!=null;

      try {
         return read (ais);
      } catch (final IOException ioe) {
         throw new IllegalArgumentException(String.format ("could not read url '%s'", url));
      }
   }

   private static double[] read (final AudioInputStream ais) throws IOException {
       assert ais!=null;
       final AudioFormat audioFormat = ais.getFormat();

       // require sampling rate = 44,100 Hz
       if (audioFormat.getSampleRate() != SAMPLE_RATE) {
           throw new IllegalArgumentException("StdAudio.read() currently supports only a sample rate of " + SAMPLE_RATE + " Hz\n"
                                             + "audio format: " + audioFormat);
       }

       // require 16-bit audio
       if (audioFormat.getSampleSizeInBits() != BITS_PER_SAMPLE) {
           throw new IllegalArgumentException("StdAudio.read() currently supports only " + BITS_PER_SAMPLE + "-bit audio\n"
                                             + "audio format: " + audioFormat);
       }

       // require little endian
       if (audioFormat.isBigEndian()) {
           throw new IllegalArgumentException("StdAudio.read() currently supports only audio stored using little endian\n"
                                             + "audio format: " + audioFormat);
       }

       /*
       int bytesToRead = ais.available();
       final byte[] bytes = new byte[bytesToRead];
       int bytesRead = ais.read(bytes);
       if (bytesToRead != bytesRead) {
            throw new IllegalStateException("read only " + bytesRead + " of " + bytesToRead + " bytes"); 
            }
*/
       final byte[] bytes = ais.readAllBytes();
       final int n = bytes.length;

       // little endian, mono
       if (audioFormat.getChannels() == MONO) {
           final double[] data = new double[n/2];
           for (int i = 0; i < n/2; i++) {
               // little endian, mono
               data[i] = ((short) (((bytes[2*i+1] & 0xFF) << 8) | (bytes[2*i] & 0xFF))) / ((double) MAX_16_BIT);
           }
           return data;
        } else if (audioFormat.getChannels() == STEREO) {
           // little endian, stereo
           final double[] data = new double[n/4];
           for (int i = 0; i < n/4; i++) {
              final double left  = ((short) (((bytes[4*i+1] & 0xFF) << 8) | (bytes[4*i + 0] & 0xFF))) / ((double) MAX_16_BIT);
              final double right = ((short) (((bytes[4*i+3] & 0xFF) << 8) | (bytes[4*i + 2] & 0xFF))) / ((double) MAX_16_BIT);
              data[i] = (left + right) / 2.0;
           }
           return data;
        } else {
           // TODO: handle big endian (or other formats)
           throw new IllegalStateException("audio format is neither mono or stereo");
        }
   }

    /**
     * Saves the double array as an audio file (using .wav or .au format).
     *
     * @param  filename the name of the audio file
     * @param  samples the array of samples
     * @throws IllegalArgumentException if unable to save {@code filename}
     * @throws IllegalArgumentException if {@code samples} is {@code null}
     * @throws IllegalArgumentException if {@code filename} is {@code null}
     * @throws IllegalArgumentException if {@code filename} extension is not {@code .wav}
     *         or {@code .au}
     */
    public static void save (final String filename, final double[] samples) {
        if (filename == null) {
            throw new IllegalArgumentException("filenameis null");
        }
        if (samples == null) {
            throw new IllegalArgumentException("samples[] is null");
        }

        // assumes 16-bit samples with sample rate = 44,100 Hz
        final byte[] data = new byte[2 * samples.length];
        for (int i = 0; i < samples.length; i++) {
            int temp = (short) (samples[i] * MAX_16_BIT);
            if (samples[i] == 1.0) temp = Short.MAX_VALUE;   // special case since 32768 not a short
            data[2*i + 0] = (byte) temp;
            data[2*i + 1] = (byte) (temp >> 8);   // little endian
        }

        // use 16-bit audio, mono, signed pulse-code modulation (PCM), little Endian
        final AudioFormat format = new AudioFormat(SAMPLE_RATE, 16, MONO, SIGNED, LITTLE_ENDIAN);

        // now save the file
        try {
            final ByteArrayInputStream bais = new ByteArrayInputStream(data);
            AudioInputStream ais = new AudioInputStream(bais, format, samples.length);
            if (filename.endsWith(".wav") || filename.endsWith(".WAV")) {
                AudioSystem.write(ais, AudioFileFormat.Type.WAVE, new File(filename));
            } else if (filename.endsWith(".au") || filename.endsWith(".AU")) {
                AudioSystem.write(ais, AudioFileFormat.Type.AU, new File(filename));
            } else {
                throw new IllegalArgumentException("file type for saving must be .wav or .au");
            }
        } catch (final IOException ioe) {
            throw new IllegalArgumentException("unable to save file '" + filename + "'", ioe);
        }
    }


    /**
     * Plays an audio file (in .wav, .mid, or .au format) in a background thread.
     *
     * @param filename the name of the audio file
     * @throws IllegalArgumentException if unable to play {@code filename}
     * @throws IllegalArgumentException if {@code filename} is {@code null}
     */
    public static synchronized void play(final String filename) {
        new Thread(new Runnable() {
            public void run() {
                AudioInputStream ais = getAudioInputStreamFromFile(filename);
                stream(ais);
            }
        }).start();
    }

    /**
     * Writes one sample (between -1.0 and +1.0) to standard audio.
     * If the sample is outside the range, it will be clipped.
     *
     * @param  sample the sample to play
     * @throws IllegalArgumentException if the sample is {@code Double.NaN}
     */
    public static void play (/*var*/ double sample) {
       if (line==null) return; // Can't play
       if (Double.isNaN(sample)) throw new IllegalArgumentException("sample is NaN");

       // clip if outside [-1, +1]
       if (sample < -1.0) sample = -1.0;
       if (sample > +1.0) sample = +1.0;

       // convert to bytes, special case for 32768 since it is not a short
       final short s = sample==1.0? Short.MAX_VALUE : (short) (MAX_16_BIT * sample);
       buffer[bufferSize++] = (byte) s;
       buffer[bufferSize++] = (byte) (s >> 8);   // little endian

       // send internal buffer to sound card if buffer is full        
       if (bufferSize >= buffer.length) {
           line.write (buffer, 0, buffer.length);
           bufferSize = 0;
       }
    }

    /**
     * Writes the array of samples (between -1.0 and +1.0) to standard audio.
     * If a sample is outside the range, it will be clipped.
     *
     * @param  samples the array of samples to play
     * @throws IllegalArgumentException if any sample is {@code Double.NaN}
     * @throws IllegalArgumentException if {@code samples} is {@code null}
     */
    public static void play (final double[] samples) {
       if (line==null) return; // Can't play
       if (samples == null) throw new IllegalArgumentException("argument to play() is null");
       for (int i = 0; i < samples.length; i++) {
          play(samples[i]);
       }
   }

   /**
    * Closes standard audio.
    */
   public static void close() {
      if (line!=null) {
         line.drain();
         line.stop();
      }
   }
    
    // https://www3.ntu.edu.sg/home/ehchua/programming/java/J8c_PlayingSound.html
    // play a wav or aif file
    // javax.sound.sampled.Clip fails for long clips (on some systems), perhaps because
    // JVM closes (see remedy in loop)
    private static void stream(AudioInputStream ais) {
        SourceDataLine line = null;
        final int BUFFER_SIZE = 4096; // 4K buffer

        try {
            AudioFormat audioFormat = ais.getFormat();
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
            line = (SourceDataLine) AudioSystem.getLine(info);
            line.open(audioFormat);
            line.start();
            byte[] samples = new byte[BUFFER_SIZE];
            int count = 0;
            while ((count = ais.read(samples, 0, BUFFER_SIZE)) != -1) {
                line.write(samples, 0, count);
            }
        } catch (final IOException e) {
            e.printStackTrace();
        } catch (final LineUnavailableException e) {
            e.printStackTrace();
        }
        finally {
            if (line != null) {
                line.drain();
                line.close();
            }
        }
    }

    /**
     * Loops an audio file (in .wav, .mid, or .au format) in a background thread.
     *
     * @param filename the name of the audio file
     * @throws IllegalArgumentException if {@code filename} is {@code null}
     */
    public static synchronized void loop(String filename) {
        if (filename == null) throw new IllegalArgumentException();

        final AudioInputStream ais = getAudioInputStreamFromFile(filename);

        try {
            Clip clip = AudioSystem.getClip();
            // Clip clip = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
            clip.open(ais);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (final LineUnavailableException e) {
            e.printStackTrace();
        } catch (final IOException e) {
            e.printStackTrace();
        }

        // keep JVM open
        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    try {
                       Thread.sleep(1000);
                    } catch (final InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }


   /***************************************************************************
    * Unit tests {@code StdAudio}.
    ***************************************************************************/

    // create a note (sine wave) of the given frequency (Hz), for the given
    // duration (seconds) scaled to the given volume (amplitude)
    private static double[] note(double hz, double duration, double amplitude) {
        int n = (int) (MyAudio.SAMPLE_RATE * duration);
        double[] a = new double[n+1];
        for (int i = 0; i <= n; i++)
            a[i] = amplitude * Math.sin(2 * Math.PI * i * hz / MyAudio.SAMPLE_RATE);
        return a;
    }

    /**
     * Test client - play an A major scale to standard audio.
     *
     * @param args the command-line arguments
     */
    /**
     * Test client - play an A major scale to standard audio.
     *
     * @param args the command-line arguments
     */
    public static void main (final String[] args) throws IOException {
        
        // 440 Hz for 1 sec
        double freq = 440.0;
        for (int i = 0; i <= MyAudio.SAMPLE_RATE; i++) {
            MyAudio.play(0.5 * Math.sin(2*Math.PI * freq * i / MyAudio.SAMPLE_RATE));
        }
        
        // scale increments
        final int[] steps = { 0, 2, 4, 5, 7, 9, 11, 12 };
        for (int i = 0; i < steps.length; i++) {
            final double hz = 440.0 * Math.pow(2, steps[i] / 12.0);
            MyAudio.play(note(hz, 1.0, 0.5));
        }

        play (read (new URL ("http://andrew.cs.fit.edu/~cse1002-stansifer/mozart/wav/T60.wav")));
        play (read (new URL ("http://andrew.cs.fit.edu/~cse1002-stansifer/mozart/wav/T61.wav")));

        // need to call this in non-interactive stuff so the program doesn't terminate
        // until all the sound leaves the speaker.
        MyAudio.close(); 
    }
}