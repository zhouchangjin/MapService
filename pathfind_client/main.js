import './style.css';
import {Map, View} from 'ol';
import MousePosition from 'ol/control/MousePosition.js';
import TileLayer from 'ol/layer/Tile';
import ImageWMS from 'ol/source/ImageWMS.js';
import TileWMS from 'ol/source/TileWMS.js';
import {Image as ImageLayer, Vector as VectorLayer} from 'ol/layer.js';
import {defaults as defaultControls} from 'ol/control.js';
import {Vector as VectorSource} from 'ol/source.js';
import Draw from 'ol/interaction/Draw.js';
import LineString from 'ol/geom/LineString.js';
import Point from 'ol/geom/Point.js';
import Feature from 'ol/Feature.js';
import {Circle, Fill, Stroke, Style} from 'ol/style.js';


let draw;
let counter=0;
const mousePositionControl = new MousePosition({
  projection: 'EPSG:4326',
  // comment the following two lines to have the mouse position
  // be placed within the map.
  className: 'custom-mouse-position',
  target: document.getElementById('mouse-position'),
});

const bounds = [115.74514099975738, 23.57042099992924,
                    120.37065699992063, 28.25341899968714];
					
const source = new VectorSource({wrapX: false});
var pathsource = new VectorSource({});
var pathnodesource =new VectorSource({});

 const linstroke = new Stroke({
   color: '#3399CC',
   width: 10,
 });
 const lineStyle=new Style({
     stroke: linstroke
   })

const vector = new VectorLayer({
  source: source,
});

const path_layer = new VectorLayer({
  source: pathsource,
  style:lineStyle,
  lname: "path"
});

const node_layer=new VectorLayer({
  source:pathnodesource,
  style:{
    'fill-color': 'rgba(255, 255, 255, 0.2)',
    'stroke-color': '#ffcc33',
    'stroke-width': 2,
    'circle-radius': 4,
    'circle-fill-color': '#ffcc33',
  },
})

const layers = [
    new TileLayer({
      source: new TileWMS({
        projection: 'EPSG:4326', // here is the source projection
        url: 'http://localhost:8080/geoserver/mymap/wms',
        params: {
          'LAYERS': 'ne:NE1_HR_LC_SR_W_DR',
        },
		params: {
                   'VERSION': '1.1.1',
                   tiled: true,
                "STYLES": '',
                "LAYERS": 'mymap:Fujian_Road',
             tilesOrigin: 115.74514099975738 + "," + 23.57042099992924
          }
      }),
    }),
	vector,
	path_layer,
  node_layer,
  
];
const map = new Map({
  layers: layers,
  target: 'map',
  view: new View({
	 projection: 'EPSG:4326',
    center: [118.89257099975,26.33921699971],
    zoom: 10,
  }),
  controls: defaultControls().extend([mousePositionControl]),
});

function addInteraction(typeStr){
	 draw = new Draw({
      source: source,
      type: typeStr,
    });
    map.addInteraction(draw);

}

const btn = document.getElementById('btnroute');
const input_start_lo=document.getElementById("start_coor_lo");
const input_start_la=document.getElementById("start_coor_la");
const input_end_lo=document.getElementById("end_coor_lo");
const input_end_la=document.getElementById("end_coor_la");
const mousepo=document.getElementById("mouse-position");
btn.onclick=(event)=>{
	//console.log(input_start_lo.value);
	//console.log(input_start_la.value);
	//console.log(input_end_lo.value);
	//console.log(input_end_la.value);
	var gpspair={
  "end": {
    "latitude": input_start_la.value,
    "longitude": input_start_lo.value
  },
  "start": {
    "latitude": input_end_la.value,
    "longitude": input_end_lo.value
	}};
  console.log(gpspair);
	
postData("http://127.0.0.1:9090/route/search", gpspair
).then((data) => {
	
  console.log(data); // JSON data parsed by `data.json()` call
  var array=[];
  
  for(var i=0;i<data.data.length;i++){
      //console.log(data.data[i])
	  var coor=[];
	  coor.push(data.data[i].longitude);
	  coor.push(data.data[i].latitude);
	  array.push(coor);
	/**
    let pathNode=new Point([data.data[i].longitude,data.data[i].latitude]);
    let pfeature=new Feature({
      geometry:pathNode,
      name:'p'+i,
      elevation:data.data[i].elevation
    })
    pathnodesource.addFeature(pfeature)
	**/
  }
  
  for(var j=0;j<array.length-1;j++){
	  let tmp=[];
	  tmp.push(array[j]);
	  tmp.push(array[j+1]);
	  let path=new LineString(tmp);
	  let feature=new Feature({
		  geometry: path,
		  name: 'path'+j,
		  elevation:data.data[j].elevation
	  });
	  pathsource.addFeature(feature);
  }

  
});
	
	
	
	
	
}

map.on('pointermove',evt=>{
	//console.log(evt);
	map.forEachFeatureAtPixel(evt.pixel, function (f,layer) {
    //selected = f;
    //selectStyle.getFill().setColor(f.get('COLOR') || '#eeeeee');
    //f.setStyle(selectStyle);
    if(layer!=null){
		//console.log(f.get('name'));
		if(layer.get('lname')!=undefined){
			document.getElementById('feature-info').innerHTML=f.get('name')+" 高度值:"+f.get('elevation');
		}
	}
	return true;
  });
	
});


map.on('click',evt=>{
	counter++;
	var str=mousepo.innerText;
	console.log("clicked map");
	console.log(mousepo.innerText);
	if(counter%2==1){
		//console.log(str.split(","))
		
		var arr=str.split(",");
		input_start_lo.value=arr[0];
		input_start_la.value=arr[1];
	}else{
		var arr=str.split(",");
		input_end_lo.value=arr[0];
		input_end_la.value=arr[1];
	}
});


//console.log(map)

async function postData(url = "", data = {}) {
  // Default options are marked with *
  const response = await fetch(url, {
    method: "POST", // *GET, POST, PUT, DELETE, etc.
    mode: "cors", // no-cors, *cors, same-origin
    cache: "no-cache", // *default, no-cache, reload, force-cache, only-if-cached
    credentials: "same-origin", // include, *same-origin, omit
    headers: {
      "Content-Type": "application/json",
      // 'Content-Type': 'application/x-www-form-urlencoded',
    },
    redirect: "follow", // manual, *follow, error
    referrerPolicy: "no-referrer", // no-referrer, *no-referrer-when-downgrade, origin, origin-when-cross-origin, same-origin, strict-origin, strict-origin-when-cross-origin, unsafe-url
    body: JSON.stringify(data), // body data type must match "Content-Type" header
  });
  return response.json(); // parses JSON response into native JavaScript objects
}

addInteraction('Point');