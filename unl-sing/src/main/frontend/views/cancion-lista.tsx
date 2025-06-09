import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { Button, ComboBox, DatePicker, Dialog, Grid, GridColumn, GridItemModel, GridSortColumn, HorizontalLayout, Icon, NumberField, Select, TextField, VerticalLayout } from '@vaadin/react-components';
import { Notification } from '@vaadin/react-components/Notification';
import { CancionService} from 'Frontend/generated/endpoints';
import { useSignal } from '@vaadin/hilla-react-signals';
import handleError from 'Frontend/views/_ErrorHandler';
import { Group, ViewToolbar } from 'Frontend/components/ViewToolbar';
import { useDataProvider } from '@vaadin/hilla-react-crud';
import { useEffect, useState } from 'react';
import Cancion from 'Frontend/generated/unl/sing/base/models/Cancion';

export const config: ViewConfig = {
  title: 'Cancion',
  menu: {
    icon: 'vaadin:clipboard-check',
    order: 1,
    title: 'Cancion',
  },
};


type CancionEntryFormProps = {
  onCancionCreated?: () => void;
};

type CancionEntryFormPropsUpdate = ()=> {
  onCancionUpdated?: () => void;
};

//GUARDAR Cancion
function CancionEntryForm(props: CancionEntryFormProps) {
  const nombre = useSignal('');
  const genero = useSignal('');
  const album = useSignal('');
  const duracion = useSignal('');
  const url = useSignal('');
  const tipo = useSignal('');
  const createCancion = async () => {
    try {
      if (nombre.value.trim().length > 0 && genero.value.trim().length > 0) {
      const id_genero = parseInt(genero.value)+1;
      const id_album= parseInt(album.value)+1;
        await CancionService.createCancion(nombre.value, id_genero, parseInt(duracion.value), url.value, tipo.value, id_album);
        if (props.onCancionCreated) {
          props.onCancionCreated();
        }
        nombre.value = '';
        genero.value = '';
        album.value= '';
        duracion.value= '';
        url.value= '';
        tipo.value= '';

        dialogOpened.value = false;
        Notification.show('Cancion creada', { duration: 5000, position: 'bottom-end', theme: 'success' });
      } else {
        Notification.show('No se pudo crear, faltan datos', { duration: 5000, position: 'top-center', theme: 'error' });
      }

    } catch (error) {
      handleError(error);
    }
  };
  
  let listaGenero = useSignal<String[]>([]);
  useEffect(() => {
    CancionService.listGeneroCombo().then(data =>
      listaGenero.value = data
    );
  }, []);
  let listaAlbum = useSignal<String[]>([]);
  useEffect(() => {
    CancionService.listAlbumCombo().then(data =>
      listaAlbum.value = data
    );
  }, []);
  let listaTipo = useSignal<String[]>([]);
  useEffect(() => {
    CancionService.listTipo().then(data =>
      listaTipo.value = data
    );
  }, []);
  const dialogOpened = useSignal(false);
  return (
    <>
      <Dialog
        modeless
        headerTitle="Nueva Cancion"
        opened={dialogOpened.value}
        onOpenedChanged={({ detail }) => {
          dialogOpened.value = detail.value;
        }}
        footer={
          <>
            <Button
              onClick={() => {
                dialogOpened.value = false;
              }}
            >
              Cancelar
            </Button>
            <Button onClick={createCancion} theme="primary">
              Registrar
            </Button>
            
          </>
        }
      >
        <VerticalLayout style={{ alignItems: 'stretch', width: '18rem', maxWidth: '100%' }}>
          <TextField label="Nombre del Cancion" 
            placeholder="Ingrese el nombre del Cancion"
            aria-label="Nombre del Cancion"
            value={nombre.value}
            onValueChanged={(evt) => (nombre.value = evt.detail.value)}
          />
          <NumberField label="Duracion" 
            placeholder="Ingrese la duracion de la cancion"
            aria-label="Nombre la duracion de la cancion"
            value={duracion.value}
            onValueChanged={(evt) => (duracion.value = evt.detail.value)}
          />
          <TextField label="Link de la cancion" 
            placeholder="Ingrese el link de la cancion"
            aria-label="Nombre el link de la cancion"
            value={url.value}
            onValueChanged={(evt) => (url.value = evt.detail.value)}
          />
          <ComboBox label="Genero" 
            items={listaGenero.value}
            placeholder='Seleccione un genero'
            aria-label='Seleccione un genero de la lista'
            value={genero.value}
            onValueChanged={(evt) => (genero.value = evt.detail.value)}
            />
            <ComboBox label="Album" 
            items={listaAlbum.value}
            placeholder='Seleccione un album'
            aria-label='Seleccione un album de la lista'
            value={album.value}
            onValueChanged={(evt) => (album.value = evt.detail.value)}
            />
            <ComboBox label="Tipo Archivo" 
            items={listaTipo.value}
            placeholder='Seleccione un tipo de archivo'
            aria-label='Seleccione un tipo de archivo de la lista'
            value={tipo.value}
            onValueChanged={(evt) => (tipo.value = evt.detail.value)}
            />
        </VerticalLayout>
      </Dialog>
      <Button
            onClick={() => {
              dialogOpened.value = true;
            }}
          >
            Agregar
          </Button>
    </>
  );
}

function indexIndex({model}:{model:GridItemModel<Cancion>}) {
  return (
    <span>
      {model.index + 1} 
    </span>
  );
}


//LISTA DE CANCIONES
export default function CancionView() {
  
  const [items, setItems] = useState([]);
  useEffect(() => {
    CancionService.listAll().then(function (data) {
      //items.values = data;
      setItems(data);
    });
  }, []);


  

  const order = (event, columnId) => {
    console.log(event);
    const direction = event.detail.value;
    console.log(`Sort direction changed for column ${columnId} to ${direction}`);
    var dir = (direction == 'asc') ? 1 : 2;
    CancionService.order(columnId, dir).then(function(data){
      setItems(data);
    });
  }

  const criterio = useSignal('');
  const texto = useSignal('');
  const itemSelect = [
    {
      label: 'Cancion',
      value: 'nombre',
    },
    {
      label: 'Album',
      value: 'album',
    },
    {
      label: 'Genero',
      value: 'genero',
    },
    {
      label: 'Tipo de Archivo',
      value: 'tipo',
    },
  ];
  const search = async () => {
    try {
      console.log(criterio.value+" "+texto.value);
      CancionService.search(criterio.value, texto.value, 0).then(function (data) {
        setItems(data);
      });

      criterio.value = '';
      texto.value = '';

      Notification.show('Busqueda realizada', { duration: 5000, position: 'bottom-end', theme: 'success' });


    } catch (error) {
      console.log(error);
      handleError(error);
    }
  };



  return (

    <main className="w-full h-full flex flex-col box-border gap-s p-m">

      <ViewToolbar title="Lista de Canciones">
        <Group>
          <CancionEntryForm />
        </Group>
      </ViewToolbar>
      <HorizontalLayout theme="spacing">
        <Select items={itemSelect}
          value={criterio.value}
          onValueChanged={(evt) => (criterio.value = evt.detail.value)}
          placeholder="Selecione un cirterio">


        </Select>

        <TextField
          placeholder="Search"
          style={{ width: '50%' }}
          value={texto.value}
          onValueChanged={(evt) => (texto.value = evt.detail.value)}
        >
          <Icon slot="prefix" icon="vaadin:search" />
        </TextField>
        <Button onClick={search} theme="primary">
          BUSCAR
        </Button>
      </HorizontalLayout>
      <Grid items={items}>
        <GridColumn  renderer={indexIndex} header="Nro" />
        <GridSortColumn  path="nombre" header="Cancion" onDirectionChanged={(e) => order(e, "nombre")}/>
        <GridSortColumn path="album" header="Album"  onDirectionChanged={(e) => order(e, "album")} />
        <GridSortColumn path="genero" header="Genero" onDirectionChanged={(e) => order(e, 'genero')}  />
        <GridSortColumn path="duracion" header="Duracion"  onDirectionChanged={(e) => order(e, "duracion")}/>
        <GridSortColumn path="tipo" header="Tipo Archivo"  onDirectionChanged={(e) => order(e, "tipo")}/>
        <GridColumn path="url" header="Link"> 
        </GridColumn>
      </Grid>
    </main>
  );
}


